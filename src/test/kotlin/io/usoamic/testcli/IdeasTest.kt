package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.enum.IdeaStatus
import io.usoamic.cli.enum.VoteType
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
import org.web3j.protocol.Web3j
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class IdeasTest {
    @Inject
    lateinit var usoamic: Usoamic

    @Inject
    lateinit var web3j: Web3j

    @Test
    fun addIdeaTest() {
        val description = generateIdeaDescription()
        val txHash = usoamic.addIdea(TestConfig.PASSWORD, description)

        while (true) {
            val transactionReceipt = web3j.ethGetTransactionReceipt(txHash).send()
            if (transactionReceipt.result != null) {
                break
            }
            println("Waiting confirmation...")
            Thread.sleep(15000)
        }

        val ideaId = getLastIdeaId()
        val idea = usoamic.getIdea(ideaId)
        assert(idea.isExist)
        assert(idea.author == usoamic.account.address)
        assert(idea.description == description)
        assert(idea.ideaId == ideaId)
        assert(idea.ideaStatus == IdeaStatus.DISCUSSION)
    }

    @Test
    fun voteForIdeaTest() {
        for(item in VoteType.values()) {
            val ideaTxHash = usoamic.addIdea(TestConfig.PASSWORD, generateIdeaDescription())

            usoamic.waitTransactionReceipt(ideaTxHash) {
                val ideaId = getLastIdeaId()
                val comment = ("Comment #" + Random.nextInt())
                val voteTxHash = when(item) {
                    VoteType.SUPPORT -> usoamic.supportIdea(TestConfig.PASSWORD, ideaId, comment)
                    VoteType.ABSTAIN -> usoamic.abstainIdea(TestConfig.PASSWORD, ideaId, comment)
                    VoteType.AGAINST -> usoamic.againstIdea(TestConfig.PASSWORD, ideaId, comment)
                }
                usoamic.waitTransactionReceipt(voteTxHash) {
                    val idea = usoamic.getIdea(ideaId)

                    when(item) {
                        VoteType.SUPPORT -> {
                            assert(idea.numberOfSupporters > BigInteger.ZERO)
                        }
                        VoteType.ABSTAIN -> {
                            assert(idea.numberOfAbstained > BigInteger.ZERO)
                        }
                        VoteType.AGAINST -> {
                            assert(idea.numberOfVotedAgainst > BigInteger.ZERO)
                        }
                    }
                    assert(idea.numberOfParticipants > BigInteger.ZERO)
                }
            }



        }
    }

    private fun getLastIdeaId(): BigInteger {
        return usoamic.getNumberOfIdeas()!!.subtract(BigInteger.ONE)
    }

    private fun generateIdeaDescription(): String {
        return "Idea #" + Random.nextInt()
    }
}
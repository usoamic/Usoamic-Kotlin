package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.enum.IdeaStatus
import io.usoamic.cli.enum.VoteType
import io.usoamic.cli.model.Vote
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.crypto.WalletUtils
import org.web3j.exceptions.MessageDecodingException
import org.web3j.protocol.Web3j
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class IdeasTest {
    @Inject
    lateinit var usoamic: Usoamic

    @Inject
    lateinit var web3j: Web3j

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun addIdeaTest() {
        addIdea { }
    }

    @Test
    fun setIdeaStatusTest() {
        val status = IdeaStatus.SPAM
        val ideaTxHash = usoamic.addIdea(TestConfig.PASSWORD, generateIdeaDescription())
        usoamic.waitTransactionReceipt(ideaTxHash) {
            val ideaId = usoamic.getLastIdeaId()
            assertThrows<MessageDecodingException> {
                val ideaStatusTxHash = usoamic.setIdeaStatus(TestConfig.PASSWORD, ideaId, status)
                usoamic.waitTransactionReceipt(ideaStatusTxHash) {
                    val idea = usoamic.getIdea(ideaId)
                    assert(idea.ideaStatus != status)
                }
            }
        }
    }

    @Test
    fun getNumberOfIdeasTest() {
        val numberOfIdeas = usoamic.getNumberOfIdeas()!!
        assert(numberOfIdeas >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfIdeasByAddressTest() {
        val numberOfIdeasByAddress = usoamic.getNumberOfIdeasByAddress(usoamic.account.address)!!
        assert(numberOfIdeasByAddress >= BigInteger.ZERO)
    }

    @Test
    fun getIdeaTest() {
        val id = BigInteger.ZERO
        val numberOfIdeas = usoamic.getNumberOfIdeas()!!

        val idea = usoamic.getIdea(id)
        val isExist = numberOfIdeas > BigInteger.ZERO

        assert(idea.isExist == isExist)
        if (isExist) {
            assert(idea.ideaId == id)
            assert(idea.description.isNotEmpty())
        }

        val noExistIdea = usoamic.getIdea(numberOfIdeas)
        assert(!noExistIdea.isExist)
    }

    @Test
    fun getIdeaByAddressTest() {
        val id = BigInteger.ZERO

        addIdea {
            val address = usoamic.account.address

            val ideaId = usoamic.getLastIdeaIdByAddress(address)

            val idea = usoamic.getIdeaByAddress(address, ideaId)
            assert(idea.isExist)
            assert(idea.ideaId == id)
            assert(idea.description.isNotEmpty())
        }
    }

    @Test
    fun getVoteTest() {
        voteForIdea(VoteType.SUPPORT) {
            val ideaRefId = usoamic.getLastIdeaId()
            val idea = usoamic.getIdea(ideaRefId)
            val voteRefId = idea.numberOfSupporters.subtract(BigInteger.ZERO)
            val vote = usoamic.getVote(ideaRefId, voteRefId)
            testVote(vote)
        }
    }

    @Test
    fun getVoteByAddressTest() {
        val address = usoamic.account.address

        voteForIdea(VoteType.SUPPORT) {
            val ideaRefId = usoamic.getLastIdeaId()
            val voteId = usoamic.getNumberOfVotesByAddress(address, ideaRefId)!!
            val vote = usoamic.getVoteByAddress(ideaRefId, address, voteId)
            testVote(vote)
            assert(vote.voter == address)
        }
    }

    @Test
    fun supportIdea() {
        this.voteForIdea(VoteType.SUPPORT) {}
    }

    @Test
    fun abstainIdea() {
        this.voteForIdea(VoteType.ABSTAIN) {}
    }

    @Test
    fun againstIdea() {
        this.voteForIdea(VoteType.AGAINST) {}
    }

    private fun voteForIdea(voteType: VoteType, callback: () -> Unit) {
        val ideaTxHash = usoamic.addIdea(TestConfig.PASSWORD, generateIdeaDescription())

        usoamic.waitTransactionReceipt(ideaTxHash) {
            val ideaId = usoamic.getLastIdeaId()
            val comment = generateComment()

            println("IdeaId: $ideaId; Comment: $comment")

            val voteTxHash = voteForIdea(voteType, ideaId, comment)
            usoamic.waitTransactionReceipt(voteTxHash) {
                val idea = usoamic.getIdea(ideaId)

                when (voteType) {
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

                val vote = usoamic.getVote(ideaId, BigInteger.ZERO)

                testVote(vote)

                assert(vote.comment == comment)
                assert(vote.voteType == voteType)
                assert(vote.voter == usoamic.account.address)

                assertThrows<MessageDecodingException> {
                    voteForIdea(voteType, ideaId, comment)
                }
            }
        }
    }

    private fun testVote(vote: Vote) {
        assert(vote.isExist)
        assert(WalletUtils.isValidAddress(vote.voter))
    }

    private fun addIdea(callback: () -> Unit) {
        val description = generateIdeaDescription()
        val txHash = usoamic.addIdea(TestConfig.PASSWORD, description)

        usoamic.waitTransactionReceipt(txHash) {
            val ideaId = usoamic.getLastIdeaId()
            val idea = usoamic.getIdea(ideaId)
            assert(idea.isExist)
            assert(idea.author == usoamic.account.address)
            assert(idea.description == description)
            assert(idea.ideaId == ideaId)
            assert(idea.ideaStatus == IdeaStatus.DISCUSSION)
            callback()
        }
    }

    private fun voteForIdea(voteType: VoteType, ideaId: BigInteger, comment: String): String {
        return when (voteType) {
            VoteType.SUPPORT -> usoamic.supportIdea(TestConfig.PASSWORD, ideaId, comment)
            VoteType.ABSTAIN -> usoamic.abstainIdea(TestConfig.PASSWORD, ideaId, comment)
            VoteType.AGAINST -> usoamic.againstIdea(TestConfig.PASSWORD, ideaId, comment)
        }
    }

    private fun generateComment(): String {
        return ("Comment #" + Random.nextInt())
    }

    private fun generateIdeaDescription(): String {
        return ("Idea #" + Random.nextInt())
    }
}
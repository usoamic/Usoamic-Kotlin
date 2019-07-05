package io.usoamic.cli.core

import io.usoamic.cli.enum.IdeaStatus
import io.usoamic.cli.enum.VoteType
import io.usoamic.cli.model.Idea
import io.usoamic.cli.model.Vote
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import java.lang.Exception
import java.math.BigInteger

open class Ideas constructor(filename: String) : Owner(filename) {
    @Throws(Exception::class)
    fun addIdea(password: String, description: String): String = executeTransaction(
        password,
        "addIdea",
        listOf(Utf8String(description))
    )

    @Throws(Exception::class)
    fun supportIdea(password: String, ideaId: BigInteger, comment: String): String = voteForIdea(password, VoteType.SUPPORT, ideaId, comment)

    @Throws(Exception::class)
    fun abstainIdea(password: String, ideaId: BigInteger, comment: String): String = voteForIdea(password, VoteType.ABSTAIN, ideaId, comment)

    @Throws(Exception::class)
    fun againstIdea(password: String, ideaId: BigInteger, comment: String): String = voteForIdea(password, VoteType.AGAINST, ideaId, comment)

    @Throws(Exception::class)
    private fun voteForIdea(password: String, voteType: VoteType, ideaId: BigInteger, comment: String): String = executeTransaction(
        password,
        when(voteType) {
            VoteType.SUPPORT -> "supportIdea"
            VoteType.ABSTAIN -> "abstainIdea"
            VoteType.AGAINST -> "againstIdea"
        },
        listOf(
            Uint256(ideaId),
            Utf8String(comment)
        )
    )
    @Throws(Exception::class)
    fun getIdea(ideaId: BigInteger): Idea {
        val function = Function(
            "getIdea",
            listOf(Uint256(ideaId)),
            listOf(
                object: TypeReference<Bool>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Address>() { },
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Uint8>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { }
            )
        )
        val result = executeCall(function)
        val ideaStatusId = result[4].value as BigInteger

        return Idea.Builder()
            .setIsExist(result[0].value as Boolean)
            .setIdeaId(result[1].value as BigInteger)
            .setAuthor(result[2].value as String)
            .setDescription(result[3].value as String)
            .setIdeaStatus(IdeaStatus.values()[ideaStatusId.toInt()])
            .setTimestamp(result[5].value as BigInteger)
            .setNumberOfSupporters(result[6].value as BigInteger)
            .setNumberOfAbstained(result[7].value as BigInteger)
            .setNumberOfVotedAgainst(result[8].value as BigInteger)
            .setNumberOfParticipants(result[9].value as BigInteger)
            .build()
    }

    @Throws(Exception::class)
    fun getVote(ideaId: BigInteger, voteId: BigInteger): Vote {
        val function = Function(
            "getVote",
            listOf(
                Uint256(ideaId),
                Uint256(voteId)
            ),
            listOf(
                object: TypeReference<Bool>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Address>() { },
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Utf8String>() { }
            )
        )
        val result = executeCall(function)

        val voteTypeId = result[4].value as BigInteger

        return Vote.Builder()
            .setIsExist(result[0].value as Boolean)
            .setIdeaId(result[1].value as BigInteger)
            .setVoteId(result[2].value as BigInteger)
            .setVoter(result[3].value as String)
            .setVoteType(VoteType.values()[voteTypeId.toInt()])
            .setComment(result[5].value as String)
            .build()
    }

    @Throws(Exception::class)
    fun getNumberOfIdeas(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getNumberOfIdeas")
}
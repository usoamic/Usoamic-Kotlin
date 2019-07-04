package io.usoamic.cli.core

import io.usoamic.cli.model.Idea
import io.usoamic.cli.model.Vote
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import java.lang.Exception


class Usoamic constructor(filename: String) : TransactionManager(filename) {
    init {

    }

    @Throws(Exception::class)
    fun balanceOf(address: String): BigInteger? {
        val function = Function(
            "balanceOf",
            listOf(Address(address)),
            listOf(object: TypeReference<Uint256>() { })
        )
        val result = executeCallSingleValueReturn(function)
        return if(result == null) result else result as BigInteger
    }

    @Throws(Exception::class)
    fun addIdea(password: String, description: String): String {
        val function = Function(
            "addIdea",
            listOf(Utf8String(description)),
            emptyList()
        )
        return executeTransaction(password, function)
    }

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
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { }
            )
        )
        val result = executeCall(function)
        return Idea.Builder()
            .setIsExist(result[0] as Boolean)
            .setIdeaId(result[1] as BigInteger)
            .setAuthor(result[2] as String)
            .setDescription(result[3] as String)
            .setIdeaStatus(result[4] as String)
            .setTimestamp(result[5] as BigInteger)
            .setNumberOfSupporters(result[6] as BigInteger)
            .setNumberOfAbstained(result[7] as BigInteger)
            .setNumberOfVotedAgainst(result[8] as BigInteger)
            .setNumberOfParticipants(result[9] as BigInteger)
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

        return Vote.Builder()
            .setIsExist(result[0] as Boolean)
            .setIdeaId(result[1] as BigInteger)
            .setVoteId(result[2] as BigInteger)
            .setVoter(result[3] as String)
            .setVoteType(result[4] as String)
            .setComment(result[5] as String)
            .build()
    }
}
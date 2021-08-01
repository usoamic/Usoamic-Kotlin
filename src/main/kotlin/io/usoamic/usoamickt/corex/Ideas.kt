package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.IdeaStatus
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.enumcls.VoteType
import io.usoamic.usoamickt.model.Idea
import io.usoamic.usoamickt.model.Vote
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import java.math.BigInteger

open class Ideas(
    contractAddress: String,
    node: String
) : Owner(
    contractAddress = contractAddress,
    node = node
) {
    fun addIdea(
        credentials: Credentials,
        description: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "addIdea",
        inputParameters = listOf(Utf8String(description)),
        txSpeed = txSpeed
    )

    fun setIdeaStatus(
        credentials: Credentials,
        ideaRefId: BigInteger,
        status: IdeaStatus,
        txSpeed: TxSpeed = TxSpeed.Auto
    ) = executeTransaction(
        credentials = credentials,
        name = "setIdeaStatus",
        inputParameters = listOf(
            Uint256(ideaRefId),
            Uint8(status.ordinal.toLong())
        ),
        txSpeed = txSpeed
    )

    fun supportIdea(
        credentials: Credentials,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String =
        voteForIdea(
            credentials = credentials,
            voteType = VoteType.SUPPORT,
            ideaRefId = ideaRefId,
            comment = comment,
            txSpeed = txSpeed
        )

    fun abstainIdea(
        credentials: Credentials,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = voteForIdea(
        credentials = credentials,
        voteType = VoteType.ABSTAIN,
        ideaRefId = ideaRefId,
        comment = comment,
        txSpeed = txSpeed
    )

    fun againstIdea(
        credentials: Credentials,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = voteForIdea(
        credentials = credentials,
        voteType = VoteType.AGAINST,
        ideaRefId = ideaRefId,
        comment = comment,
        txSpeed = txSpeed
    )

    private fun voteForIdea(
        credentials: Credentials,
        voteType: VoteType,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed
    ): String = executeTransaction(
        credentials = credentials,
        name = when (voteType) {
            VoteType.SUPPORT -> "supportIdea"
            VoteType.ABSTAIN -> "abstainIdea"
            VoteType.AGAINST -> "againstIdea"
        },
        inputParameters = listOf(
            Uint256(ideaRefId),
            Utf8String(comment)
        ),
        txSpeed = txSpeed
    )

    fun getIdea(
        addressOfRequester: String,
        ideaRefId: BigInteger
    ): Idea = getAndPrepareIdea(
        addressOfRequester = addressOfRequester,
        name = "getIdea",
        inputParameters = listOf(
            Uint256(ideaRefId)
        )
    )

    fun getIdeaByAuthor(
        author: String,
        ideaId: BigInteger
    ): Idea = getAndPrepareIdea(
        addressOfRequester = author,
        name = "getIdeaByAuthor",
        inputParameters = listOf(
            Address(author),
            Uint256(ideaId)
        )
    )

    fun getVote(
        addressOfRequester: String,
        ideaRefId: BigInteger,
        voteRefId: BigInteger
    ): Vote = getAndPrepareVote(
        addressOfRequester = addressOfRequester,
        name= "getVote",
        inputParameters = listOf(
            Uint256(ideaRefId),
            Uint256(voteRefId)
        ),
        voteRefId = voteRefId
    )

    fun getVoteByVoter(voter: String, voteId: BigInteger): Vote = getAndPrepareVote(
        addressOfRequester = voter,
        name = "getVoteByVoter",
        inputParameters = listOf(
            Address(voter),
            Uint256(voteId)
        )
    )

    fun getNumberOfIdeas(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        addressOfRequester = addressOfRequester,
        name = "getNumberOfIdeas"
    )

    fun getNumberOfIdeasByAuthor(author: String): BigInteger? = executeCallUint256ValueReturn(
        addressOfRequester = author,
        name = "getNumberOfIdeasByAuthor",
        inputParameters = listOf(
            Address(author)
        )
    )

    fun getNumberOfVotesByVoter(voter: String): BigInteger? = executeCallUint256ValueReturn(
        addressOfRequester = voter,
        name = "getNumberOfVotesByVoter",
        inputParameters = listOf(Address(voter))
    )

    fun getLastIdeaId(addressOfRequester: String): BigInteger = getNumberOfIdeas(addressOfRequester)!!.subtract(BigInteger.ONE)

    fun getLastIdeaIdByAuthor(author: String): BigInteger = getNumberOfIdeasByAuthor(author)!!.subtract(BigInteger.ONE)

    private fun getAndPrepareIdea(addressOfRequester: String, name: String, inputParameters: List<Type<out Any>>): Idea {
        val result = executeCall(
            addressOfRequester = addressOfRequester,
            function = Function(
                name,
                inputParameters,
                getIdeaOutputParameters()
            )
        )
        val ideaStatusId = result[5].value as BigInteger

        return Idea.Builder()
            .setIsExist(result[0].value as Boolean)
            .setIdeaId(result[1].value as BigInteger)
            .setIdeaRefId(result[2].value as BigInteger)
            .setAuthor(result[3].value as String)
            .setDescription(result[4].value as String)
            .setIdeaStatus(IdeaStatus.values()[ideaStatusId.toInt()])
            .setTimestamp(result[6].value as BigInteger)
            .setNumberOfSupporters(result[7].value as BigInteger)
            .setNumberOfAbstained(result[8].value as BigInteger)
            .setNumberOfVotedAgainst(result[9].value as BigInteger)
            .setNumberOfParticipants(result[10].value as BigInteger)
            .build()
    }

    private fun getAndPrepareVote(
        addressOfRequester: String,
        name: String,
        inputParameters: List<Type<out Any>>,
        voteRefId: BigInteger = BigInteger("-1")
    ): Vote {
        val result = executeCall(
            addressOfRequester = addressOfRequester,
            function = Function(
                name,
                inputParameters,
                getVoteOutputParameters()
            )
        )

        val voteTypeId = result[4].value as BigInteger

        return Vote.Builder()
            .setIsExist(result[0].value as Boolean)
            .setIdeaRefId(result[1].value as BigInteger)
            .setVoteRefId(voteRefId)
            .setVoteId(result[2].value as BigInteger)
            .setVoter(result[3].value as String)
            .setVoteType(VoteType.values()[voteTypeId.toInt()])
            .setComment(result[5].value as String)
            .build()
    }

    private fun getIdeaOutputParameters() = listOf(
        object : TypeReference<Bool>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Address>() {},
        object : TypeReference<Utf8String>() {},
        object : TypeReference<Uint8>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {}
    )

    private fun getVoteOutputParameters() = listOf(
        object : TypeReference<Bool>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Address>() {},
        object : TypeReference<Uint256>() {},
        object : TypeReference<Utf8String>() {}
    )
}
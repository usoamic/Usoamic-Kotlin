package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.IdeaStatus
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Idea
import io.usoamic.usoamickt.model.Vote
import java.math.BigInteger


interface Ideas {
    fun addIdea(
        password: String,
        description: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun setIdeaStatus(
        password: String,
        ideaRefId: BigInteger,
        status: IdeaStatus,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun supportIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun abstainIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun againstIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun getIdea(ideaRefId: BigInteger): Idea

    fun getIdeaByAuthor(
        author: String,
        ideaId: BigInteger
    ): Idea

    fun getVote(
        ideaRefId: BigInteger,
        voteRefId: BigInteger
    ): Vote

    fun getVoteByVoter(
        voter: String,
        voteId: BigInteger
    ): Vote

    fun getNumberOfIdeas(): BigInteger?

    fun getNumberOfIdeasByAuthor(author: String): BigInteger?

    fun getNumberOfVotesByVoter(voter: String): BigInteger?

    fun getLastIdeaId(): BigInteger

    fun getLastIdeaIdByAuthor(author: String): BigInteger
}
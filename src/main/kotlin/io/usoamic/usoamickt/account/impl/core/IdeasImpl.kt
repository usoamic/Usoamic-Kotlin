package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.Ideas
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.IdeaStatus
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Idea
import io.usoamic.usoamickt.model.Vote
import java.math.BigInteger

open class IdeasImpl constructor(
    private val usoamic: Usoamic
) : OwnerImpl(usoamic),
    Ideas {
    override fun addIdea(
        password: String,
        description: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.addIdea(
            password = password,
            description = description,
            txSpeed = txSpeed
        )
    }

    override fun setIdeaStatus(
        password: String,
        ideaRefId: BigInteger,
        status: IdeaStatus,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setIdeaStatus(
            password = password,
            ideaRefId = ideaRefId,
            status = status,
            txSpeed = txSpeed
        )
    }

    override fun supportIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.supportIdea(
            password = password,
            ideaRefId = ideaRefId,
            comment = comment,
            txSpeed = txSpeed
        )
    }

    override fun abstainIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.abstainIdea(
            password = password,
            ideaRefId = ideaRefId,
            comment = comment,
            txSpeed = txSpeed
        )
    }

    override fun againstIdea(
        password: String,
        ideaRefId: BigInteger,
        comment: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.againstIdea(
            password = password,
            ideaRefId = ideaRefId,
            comment = comment,
            txSpeed = txSpeed
        )
    }

    override fun getIdea(ideaRefId: BigInteger): Idea {
        return usoamic.getIdea(ideaRefId)
    }

    override fun getIdeaByAuthor(
        author: String,
        ideaId: BigInteger
    ): Idea {
        return usoamic.getIdeaByAuthor(
            author = author,
            ideaId = ideaId
        )
    }

    override fun getVote(
        ideaRefId: BigInteger,
        voteRefId: BigInteger
    ): Vote {
        return usoamic.getVote(
            ideaRefId = ideaRefId,
            voteRefId = voteRefId
        )
    }

    override fun getVoteByVoter(
        voter: String,
        voteId: BigInteger
    ): Vote {
        return usoamic.getVoteByVoter(
            voter = voter,
            voteId = voteId
        )
    }

    override fun getNumberOfIdeas(): BigInteger? {
        return usoamic.getNumberOfIdeas()
    }

    override fun getNumberOfIdeasByAuthor(author: String): BigInteger? {
        return usoamic.getNumberOfIdeasByAuthor(author)
    }

    override fun getNumberOfVotesByVoter(voter: String): BigInteger? {
        return usoamic.getNumberOfVotesByVoter(voter)
    }

    override fun getLastIdeaId(): BigInteger {
        return usoamic.getLastIdeaId()
    }

    override fun getLastIdeaIdByAuthor(author: String): BigInteger {
        return usoamic.getLastIdeaIdByAuthor(author)
    }

}
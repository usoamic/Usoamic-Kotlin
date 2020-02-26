package io.usoamic.usoamickt.model

import io.usoamic.usoamickt.enumcls.VoteType
import java.math.BigInteger

data class Vote constructor(
    val isExist: Boolean,
    val ideaRefId: BigInteger,
    val voteRefId: BigInteger,
    val voteId: BigInteger,
    val voter: String,
    val voteType: VoteType,
    val comment: String
) {
    class Builder {
        private var isExist: Boolean = false
        private lateinit var ideaRefId: BigInteger
        private lateinit var voteId: BigInteger
        private lateinit var voteRefId: BigInteger
        private lateinit var voter: String
        private lateinit var voteType: VoteType
        private lateinit var comment: String

        fun setIsExist(exist: Boolean) = apply {
            this.isExist = exist
        }

        fun setIdeaRefId(ideaId: BigInteger) = apply {
            this.ideaRefId = ideaId
        }

        fun setVoteId(voteId: BigInteger) = apply {
            this.voteId = voteId
        }

        fun setVoteRefId(voteRefId: BigInteger) = apply {
            this.voteRefId = voteRefId
        }

        fun setVoter(address: String) = apply {
            this.voter = address
        }

        fun setVoteType(voteType: VoteType) = apply {
            this.voteType = voteType
        }

        fun setComment(comment: String) = apply {
            this.comment = comment
        }

        fun build() = Vote(
            isExist,
            ideaRefId,
            if(::voteRefId.isInitialized) voteRefId else BigInteger("-1"),
            voteId,
            voter,
            voteType,
            comment
        )
    }
}
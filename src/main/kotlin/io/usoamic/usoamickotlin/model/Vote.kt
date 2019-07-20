package io.usoamic.usoamickotlin.model

import io.usoamic.usoamickotlin.enum.VoteType
import java.math.BigInteger

data class Vote constructor(
    val isExist: Boolean,
    val ideaId: BigInteger,
    val voteId: BigInteger,
    val voter: String,
    val voteType: VoteType,
    val comment: String
) {
    class Builder {
        private var isExist: Boolean = false
        private lateinit var ideaId: BigInteger
        private lateinit var voteId: BigInteger
        private lateinit var voter: String
        private lateinit var voteType: VoteType
        private lateinit var comment: String

        fun setIsExist(exist: Boolean) = apply {
            this.isExist = exist
        }

        fun setIdeaId(ideaId: BigInteger) = apply {
            this.ideaId = ideaId
        }

        fun setVoteId(voteId: BigInteger) = apply {
            this.voteId = voteId
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
            ideaId,
            voteId,
            voter,
            voteType,
            comment
        )
    }
}
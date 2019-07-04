package io.usoamic.cli.model

import java.math.BigInteger
import java.sql.Timestamp

data class Idea constructor(
    val isExist: Boolean,
    val ideaId: BigInteger,
    val author: String,
    val description: String,
    val ideaStatus: String,
    val timestamp: BigInteger,
    val numberOfSupporters: BigInteger,
    val numberOfAbstained: BigInteger,
    val numberOfVotedAgainst: BigInteger,
    val numberOfParticipants: BigInteger
) {
    class Builder {
        private var isExist: Boolean = false
        private lateinit var ideaId: BigInteger
        private lateinit var author: String
        private lateinit var description: String
        private lateinit var ideaStatus: String
        private lateinit var timestamp: BigInteger
        private lateinit var numberOfSupporters: BigInteger
        private lateinit var numberOfAbstained: BigInteger
        private lateinit var numberOfVotedAgainst: BigInteger
        private lateinit var numberOfParticipants: BigInteger

        fun setIsExist(isExist: Boolean) = apply {
            this.isExist = isExist
        }

        fun setIdeaId(ideaId: BigInteger) = apply {
            this.ideaId = ideaId
        }

        fun setAuthor(author: String) = apply {
            this.author = author
        }

        fun setDescription(description: String) = apply {
            this.description = description
        }

        fun setIdeaStatus(ideaStatus: String) = apply {
            this.ideaStatus = ideaStatus
        }

        fun setTimestamp(timestamp: BigInteger) = apply {
            this.timestamp = timestamp
        }

        fun setNumberOfSupporters(numberOfSupporters: BigInteger) = apply {
            this.numberOfSupporters = numberOfSupporters
        }

        fun setNumberOfAbstained(numberOfAbstained: BigInteger) = apply {
            this.numberOfAbstained = numberOfAbstained
        }

        fun setNumberOfVotedAgainst(numberOfVotedAgainst: BigInteger) = apply {
            this.numberOfVotedAgainst = numberOfVotedAgainst
        }

        fun setNumberOfParticipants(numberOfParticipants: BigInteger) = apply {
            this.numberOfParticipants = numberOfParticipants
        }

        fun build() = Idea(
            isExist,
            ideaId,
            author,
            description,
            ideaStatus,
            timestamp,
            numberOfSupporters,
            numberOfAbstained,
            numberOfVotedAgainst,
            numberOfParticipants
        )
    }
}
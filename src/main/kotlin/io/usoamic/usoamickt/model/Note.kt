package io.usoamic.usoamickt.model

import io.usoamic.usoamickt.enumcls.NoteType
import java.math.BigInteger

data class Note constructor(
    val isExist: Boolean,
    val noteId: BigInteger,
    val type: NoteType,
    val noteRefId: BigInteger,
    val content: String,
    val author: String,
    val timestamp: BigInteger
){
    class Builder {
        private var isExist: Boolean = false
        private lateinit var noteId: BigInteger
        private lateinit var type: NoteType
        private lateinit var noteRefId: BigInteger
        private lateinit var content: String
        private lateinit var author: String
        private lateinit var timestamp: BigInteger

        fun setIsExist(isExist: Boolean) = apply {
            this.isExist = isExist
        }

        fun setNoteId(noteId: BigInteger) = apply {
            this.noteId = noteId
        }

        fun setVisibility(type: NoteType) = apply {
            this.type = type
        }

        fun setNoteRefId(refId: BigInteger) = apply {
            this.noteRefId = refId
        }

        fun setContent(content: String) = apply {
            this.content = content
        }

        fun setAuthor(author: String) = apply {
            this.author = author
        }

        fun setTimestamp(timestamp: BigInteger) = apply {
            this.timestamp = timestamp
        }

        fun build() = Note(
            isExist,
            noteId,
            type,
            noteRefId,
            content,
            author,
            timestamp
        )
    }
}
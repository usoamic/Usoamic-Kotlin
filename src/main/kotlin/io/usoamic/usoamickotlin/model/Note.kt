package io.usoamic.usoamickotlin.model

import io.usoamic.usoamickotlin.enum.NoteVisibility
import java.math.BigInteger

data class Note constructor(
    val isExist: Boolean,
    val noteId: BigInteger,
    val visibility: NoteVisibility,
    val noteRefId: BigInteger,
    val content: String,
    val author: String,
    val timestamp: BigInteger
){
    class Builder {
        private var isExist: Boolean = false
        private lateinit var noteId: BigInteger
        private lateinit var visibility: NoteVisibility
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

        fun setVisibility(visibility: NoteVisibility) = apply {
            this.visibility = visibility
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
            visibility,
            noteRefId,
            content,
            author,
            timestamp
        )
    }
}
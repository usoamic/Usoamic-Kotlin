package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.Notes
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Note
import java.math.BigInteger

open class NotesImpl constructor(
    private val usoamic: Usoamic
) : IdeasImpl(usoamic),
    Notes {
    override fun addPublicNote(
        password: String,
        content: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.addPublicNote(
            password = password,
            content = content,
            txSpeed = txSpeed
        )
    }

    override fun addUnlistedNote(
        password: String,
        content: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.addUnlistedNote(
            password = password,
            content = content,
            txSpeed = txSpeed
        )
    }

    override fun getNumberOfPublicNotes(): BigInteger? {
        return usoamic.getNumberOfPublicNotes()
    }

    override fun getNumberOfNotesByAuthor(address: String): BigInteger? {
        return usoamic.getNumberOfNotesByAuthor(address)
    }

    override fun getLastNoteId(): BigInteger {
        return usoamic.getLastNoteId()
    }

    override fun getLastNoteIdByAddress(address: String): BigInteger {
        return usoamic.getLastNoteIdByAddress(address)
    }

    override fun getNoteByAuthor(
        author: String,
        noteId: BigInteger
    ): Note {
        return usoamic.getNoteByAuthor(
            author = author,
            noteId = noteId
        )
    }

    override fun getNote(noteRefId: BigInteger): Note {
        return usoamic.getNote(noteRefId)
    }

}
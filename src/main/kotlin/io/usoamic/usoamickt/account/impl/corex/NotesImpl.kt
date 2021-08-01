package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.Notes
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Note
import java.math.BigInteger

open class NotesImpl constructor(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : IdeasImpl(
    fileName = fileName,
    filePath = filePath,
    usoamic = usoamic
),
    Notes {
    override fun addPublicNote(
        password: String,
        content: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.addPublicNote(
            credentials = getCredentials(password),
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
            credentials = getCredentials(password),
            content = content,
            txSpeed = txSpeed
        )
    }

    override fun getNumberOfPublicNotes(): BigInteger? {
        return usoamic.getNumberOfPublicNotes(
            addressOfRequester = address
        )
    }

    override fun getNumberOfNotesByAuthor(address: String): BigInteger? {
        return usoamic.getNumberOfNotesByAuthor(address)
    }

    override fun getLastNoteId(): BigInteger {
        return usoamic.getLastNoteId(
            addressOfRequester = address
        )
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
        return usoamic.getNote(
            addressOfRequester = address,
            noteRefId = noteRefId
        )
    }

}
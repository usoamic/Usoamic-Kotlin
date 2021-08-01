package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Note
import java.math.BigInteger


interface Notes {
    fun addPublicNote(
        password: String,
        content: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun addUnlistedNote(
        password: String,
        content: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun getNumberOfPublicNotes(): BigInteger?

    fun getNumberOfNotesByAuthor(
        address: String
    ): BigInteger?

    fun getLastNoteId(): BigInteger

    fun getLastNoteIdByAddress(
        address: String
    ): BigInteger

    fun getNoteByAuthor(
        author: String,
        noteId: BigInteger
    ): Note

    fun getNote(
        noteRefId: BigInteger
    ): Note
}
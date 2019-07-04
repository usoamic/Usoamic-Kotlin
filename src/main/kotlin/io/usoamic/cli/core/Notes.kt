package io.usoamic.cli.core

import io.usoamic.cli.enum.NoteVisibility
import io.usoamic.cli.enum.VoteType
import io.usoamic.cli.model.Note
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import java.math.BigInteger

open class Notes constructor(filename: String) : TransactionManager(filename) {
    @Throws(Exception::class)
    fun addPublicNote(password: String, content: String): String = addNote(password, NoteVisibility.PUBLIC, content)

    @Throws(Exception::class)
    fun addUnlistedNote(password: String, content: String): String = addNote(password, NoteVisibility.UNLISTED, content)

    @Throws(Exception::class)
    private fun addNote(password: String, noteVisibility: NoteVisibility, content: String): String {
        val function = Function(
            when(noteVisibility) {
                NoteVisibility.PUBLIC -> "addPublicNote"
                NoteVisibility.UNLISTED -> "addUnlistedNote"
            },
            listOf(Utf8String(content)),
            emptyList()
        )
        return executeTransaction(password, function)
    }

    @Throws(Exception::class)
    fun getNoteByAddress(author: String, noteId: BigInteger): Note {
        val function = Function(
            "getNoteByAddress",
            listOf(
                Utf8String(author),
                Uint256(noteId)
            ),
            listOf(
                object: TypeReference<Bool>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint8>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Address>() { },
                object: TypeReference<Uint256>() { }
            )
        )
        val result = executeCall(function)
        val visibilityId = result[2].value as BigInteger

        return Note.Builder()
            .setIsExist(result[0].value as Boolean)
            .setNoteId(result[1].value as BigInteger)
            .setVisibility(NoteVisibility.values()[visibilityId.toInt()])
            .setRefId(result[3].value as BigInteger)
            .setContent(result[4].value as String)
            .setAuthor(result[5].value as String)
            .setTimestamp(result[6].value as BigInteger)
            .build()
    }
}
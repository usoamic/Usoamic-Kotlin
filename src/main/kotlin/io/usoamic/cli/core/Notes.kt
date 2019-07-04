package io.usoamic.cli.core

import io.usoamic.cli.enum.NoteVisibility
import io.usoamic.cli.enum.VoteType
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256

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
            listOf(
                Utf8String(content)
            ),
            emptyList()
        )
        return executeTransaction(password, function)
    }
}
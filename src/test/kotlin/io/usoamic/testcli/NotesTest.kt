package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.enum.NoteVisibility
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
import org.web3j.crypto.WalletUtils
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextUInt

class NotesTest {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun addPublicNoteTest() {
        addNoteTest(NoteVisibility.PUBLIC)
    }

    @Test
    fun addUnlistedNoteTest() {
        addNoteTest(NoteVisibility.UNLISTED)
    }

    private fun addNoteTest(noteVisibility: NoteVisibility) {
        val content = generateNoteContent()
        val addNoteTxHash = when (noteVisibility) {
            NoteVisibility.PUBLIC -> usoamic.addPublicNote(TestConfig.PASSWORD, content)
            NoteVisibility.UNLISTED -> usoamic.addUnlistedNote(TestConfig.PASSWORD, content)
        }

        usoamic.waitTransactionReceipt(addNoteTxHash) {
            val author = usoamic.account.address
            val noteId = usoamic.getLastNoteIdByAddress(author)!!
            val note = usoamic.getNoteByAddress(author, noteId)
            println(note)

            assert(note.isExist)
            assert(note.content == content)
            assert(note.author == author)
            assert(note.visibility == noteVisibility)

            if(noteVisibility == NoteVisibility.PUBLIC) {
                val publicNoteId = usoamic.getLastNoteId()!!
                val commonNote = usoamic.getNote(publicNoteId)
                assert(commonNote == note)
            }
        }
    }

    @Test
    fun getNoteTest() {
        val id = BigInteger.ZERO
        val note = usoamic.getNote(id)
        val numberOfNotes = usoamic.getNumberOfPublicNotes()!!
        val isExist = numberOfNotes > BigInteger.ZERO

        assert(note.isExist == isExist)
        if(isExist) {
            assert(!note.content.isEmpty())
            assert(WalletUtils.isValidAddress(note.author))
        }

        val noExistNote = usoamic.getNote(numberOfNotes)
        assert(!noExistNote.isExist)
    }

    @Test
    fun getNumberOfPublicNotesTest() {
        val numberOfNotes = usoamic.getNumberOfPublicNotes()!!
        assert(numberOfNotes >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfNotesByAddressTest() {
        val numberOfNotes = usoamic.getNumberOfNotesByAddress(usoamic.account.address)!!
        assert(numberOfNotes >= BigInteger.ZERO)
    }

    private fun generateNoteContent(): String {
        return "Note #" + Random.nextInt()
    }
}
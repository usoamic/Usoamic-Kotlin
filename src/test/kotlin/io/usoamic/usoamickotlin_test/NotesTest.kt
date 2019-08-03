package io.usoamic.usoamickotlin_test

import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.enum.NoteVisibility
import io.usoamic.usoamickotlin.model.Note
import io.usoamic.usoamickotlin_test.other.TestConfig
import org.junit.jupiter.api.Test
import org.web3j.crypto.WalletUtils
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

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
            val author = usoamic.address
            val noteId = usoamic.getLastNoteIdByAddress(author)!!
            val note = usoamic.getNoteByAuthor(author, noteId)

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
    fun getNoteByAddressTest() {
        val address = usoamic.address

        val id = BigInteger.ZERO
        val numberOfNotes = usoamic.getNumberOfNotesByAuthor(address)!!

        val note = usoamic.getNoteByAuthor(address, id)
        val noExistNote = usoamic.getNoteByAuthor(address, numberOfNotes)

        testNote(note, noExistNote, numberOfNotes)
    }

    @Test
    fun getNoteTest() {
        val id = BigInteger.ZERO
        val numberOfNotes = usoamic.getNumberOfPublicNotes()!!

        val noExistNote = usoamic.getNote(numberOfNotes)
        val note = usoamic.getNote(id)

        testNote(note, noExistNote, numberOfNotes)
    }

    private fun testNote(note: Note, noExistNote: Note, numberOfNotes: BigInteger) {
        val isExist = numberOfNotes > BigInteger.ZERO

        assert(note.isExist == isExist)
        if(isExist) {
            assert(note.content.isNotEmpty())
            assert(WalletUtils.isValidAddress(note.author))
        }

        assert(!noExistNote.isExist)
    }

    @Test
    fun getNumberOfPublicNotesTest() {
        val numberOfNotes = usoamic.getNumberOfPublicNotes()!!
        assert(numberOfNotes >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfNotesByAddressTest() {
        val numberOfNotes = usoamic.getNumberOfNotesByAuthor(usoamic.address)!!
        assert(numberOfNotes >= BigInteger.ZERO)
    }

    private fun generateNoteContent(): String {
        return "Note #" + Random.nextInt()
    }
}
package io.usoamic.usoamickt_test

import io.usoamic.usoamickt.account.api.UsoamicAccount
import io.usoamic.usoamickt.enumcls.NoteType
import io.usoamic.usoamickt.model.Note
import io.usoamic.usoamickt_test.other.TestConfig
import org.junit.jupiter.api.Test
import org.web3j.crypto.WalletUtils
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class NotesTest {
    @Inject
    lateinit var usoamic: UsoamicAccount

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun addPublicNoteTest() {
        addNoteTest(NoteType.PUBLIC)
    }

    @Test
    fun addUnlistedNoteTest() {
        addNoteTest(NoteType.UNLISTED)
    }

    private fun addNoteTest(noteType: NoteType) {
        val content = generateNoteContent()
        val addNoteTxHash = when (noteType) {
            NoteType.PUBLIC -> usoamic.addPublicNote(TestConfig.PASSWORD, content)
            NoteType.UNLISTED -> usoamic.addUnlistedNote(TestConfig.PASSWORD, content)
        }

        usoamic.waitTransactionReceipt(addNoteTxHash) {
            val author = usoamic.address
            val noteId = usoamic.getLastNoteIdByAddress(author)!!
            val note = usoamic.getNoteByAuthor(author, noteId)

            assert(note.isExist)
            assert(note.content == content)
            assert(note.author == author)
            assert(note.type == noteType)

            if(noteType == NoteType.PUBLIC) {
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
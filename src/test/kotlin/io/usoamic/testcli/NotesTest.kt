package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.enum.NoteVisibility
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
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
            val noteId = usoamic.getLastNoteIdByAddress(author)
            val note = usoamic.getNote(noteId!!)
            assert(note.isExist)
            assert(note.content == content)
            assert(note.author == author)
            assert(note.visibility == noteVisibility)

            val commonNote = usoamic.getNoteByAddress(author, noteId)
            assert(commonNote == note)
        }
    }

    private fun generateNoteContent(): String {
        return "Note #" + Random.nextInt()
    }
}
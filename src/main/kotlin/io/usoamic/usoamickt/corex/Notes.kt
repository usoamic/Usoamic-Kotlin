package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.NoteType
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Note
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import java.math.BigInteger

open class Notes(
    contractAddress: String,
    node: String
) : Ideas(
    contractAddress = contractAddress,
    node = node
) {
    fun addPublicNote(credentials: Credentials, content: String, txSpeed: TxSpeed = TxSpeed.Auto): String = addNote(
        credentials = credentials,
        noteType = NoteType.PUBLIC,
        content = content,
        txSpeed = txSpeed
    )

    fun addUnlistedNote(credentials: Credentials, content: String, txSpeed: TxSpeed = TxSpeed.Auto): String = addNote(
        credentials = credentials,
        noteType = NoteType.UNLISTED,
        content = content,
        txSpeed = txSpeed
    )

    private fun addNote(
        credentials: Credentials,
        noteType: NoteType,
        content: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = when (noteType) {
            NoteType.PUBLIC -> "addPublicNote"
            NoteType.UNLISTED -> "addUnlistedNote"
        },
        inputParameters = listOf(
            Utf8String(content)
        ),
        txSpeed = txSpeed
    )

    fun getNumberOfPublicNotes(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        name = "getNumberOfPublicNotes",
        addressOfRequester = addressOfRequester
    )

    fun getNumberOfNotesByAuthor(address: String): BigInteger? = executeCallUint256ValueReturn(
        name = "getNumberOfNotesByAuthor",
        addressOfRequester = address,
        inputParameters = listOf(
            Address(address)
        )
    )

    fun getLastNoteId(addressOfRequester: String): BigInteger = getNumberOfPublicNotes(addressOfRequester)!!.subtract(BigInteger.ONE)

    fun getLastNoteIdByAddress(address: String): BigInteger =
        getNumberOfNotesByAuthor(address)!!.subtract(BigInteger.ONE)

    fun getNoteByAuthor(
        author: String,
        noteId: BigInteger
    ): Note = getAndPrepareNote(
        addressOfRequester = author,
        name = "getNoteByAuthor",
        inputParameters = listOf(
            Address(author),
            Uint256(noteId)
        )
    )

    fun getNote(
        addressOfRequester: String,
        noteRefId: BigInteger
    ): Note = getAndPrepareNote(
        addressOfRequester = addressOfRequester,
        name = "getNote",
        inputParameters = listOf(
            Uint256(
                noteRefId
            )
        )
    )

    private fun getAndPrepareNote(
        addressOfRequester: String,
        name: String,
        inputParameters: List<Type<out Any>>
    ): Note {
        val result = executeCall(
            addressOfRequester = addressOfRequester,
            function = Function(
                name,
                inputParameters,
                listOf(
                    object : TypeReference<Bool>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Uint8>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Utf8String>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Uint256>() {}
                )
            )
        )
        val noteTypeId = result[2].value as BigInteger

        return Note.Builder()
            .setIsExist(result[0].value as Boolean)
            .setNoteId(result[1].value as BigInteger)
            .setVisibility(NoteType.values()[noteTypeId.toInt()])
            .setNoteRefId(result[3].value as BigInteger)
            .setContent(result[4].value as String)
            .setAuthor(result[5].value as String)
            .setTimestamp(result[6].value as BigInteger)
            .build()
    }
}
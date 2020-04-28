package io.usoamic.usoamickt_test

import io.usoamic.usoamickt.exception.*
import io.usoamic.usoamickt.util.ValidateUtil
import io.usoamic.usoamickt_test.other.TestConfig
import org.junit.jupiter.api.Test

class ValidateUtilTest {
    @Test
    fun validatePasswordTest() {
        ValidateUtil.validatePassword("1!")

        try {
            ValidateUtil.validatePassword("")
        } catch (e: Exception) {
            assert(e is EmptyPasswordException)
        }
    }

    @Test
    fun validateDescriptionTest() {
        ValidateUtil.validateDescription("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")

        try {
            ValidateUtil.validateDescription("")
        } catch (e: Exception) {
            assert(e is EmptyDescriptionException)
        }
    }

    @Test
    fun validateNoteContentTest() {
        ValidateUtil.validateNoteContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        try {
            ValidateUtil.validateNoteContent("")
        } catch (e: Exception) {
            assert(e is EmptyNoteContentException)
        }
    }

    @Test
    fun validateCommentTest() {
        ValidateUtil.validateComment("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.")
        try {
            ValidateUtil.validateComment("")
        } catch (e: Exception) {
            assert(e is EmptyCommentException)
        }
    }

    @Test
    fun validatePrivateKeyTest() {
        ValidateUtil.validatePrivateKey("d5d97f6ab07c3de19e4b7a67fc5f3458b4faf968a109aaeec327e4c2cd0ab484")
        try {
            ValidateUtil.validatePrivateKey("")
        } catch (e: Exception) {
            assert(e is EmptyPrivateKeyException)
        }

        try {
            ValidateUtil.validatePrivateKey("0x0")
        } catch (e: Exception) {
            assert(e is InvalidPrivateKeyException)
        }
    }

    @Test
    fun validateMnemonicPhraseTest() {
        ValidateUtil.validateMnemonicPhrase("denial wrist culture into guess parade lesson black member shove wisdom strike")

        try {
            ValidateUtil.validateMnemonicPhrase("")
        } catch (e: Exception) {
            assert(e is EmptyMnemonicPhraseException)
        }

        try {
            ValidateUtil.validateMnemonicPhrase("qwerty123")
        } catch (e: Exception) {
            assert(e is InvalidMnemonicPhraseException)
        }
    }

    @Test
    fun validateAddressTest() {
        val address = TestConfig.CONTRACT_CREATOR_ADDRESS
        ValidateUtil.validateAddress(address)

        try {
            ValidateUtil.validateAddress("")
        } catch (e: Exception) {
            assert(e is EmptyAddressException)
        }

        try {
            ValidateUtil.validateAddress("0x01")
        } catch (e: Exception) {
            assert(e is InvalidAddressException)
        }
    }

    @Test
    fun validateTransferValueTest() {
        ValidateUtil.validateTransferValue("1")

        try {
            ValidateUtil.validateTransferValue("")
        } catch (e: Exception) {
            assert(e is EmptyValueException)
        }

        try {
            ValidateUtil.validateTransferValue("qwerty")
        } catch (e: Exception) {
            e.printStackTrace()
            assert(e is InvalidValueException)
        }
    }

    @Test
    fun validateAppIdTest() {
        ValidateUtil.validateAppId("io.usoamic.app")
        try {
            ValidateUtil.validateAppId("")
        } catch (e: Exception) {
            assert(e is EmptyAppIdException)
        }
    }

    @Test
    fun validatePurchaseIdTest() {
        ValidateUtil.validatePurchaseId("io.usoamic.app.purchase1")
        try {
            ValidateUtil.validatePurchaseId("")
        } catch (e: Exception) {
            assert(e is EmptyPurchaseId)
        }
    }

    @Test
    fun validateIdTest() {
        ValidateUtil.validateId("1")
        try {
            ValidateUtil.validateId("")
        } catch (e: Exception) {
            assert(e is InvalidIdException)
        }

        try {
            ValidateUtil.validateId("-1")
        } catch (e: Exception) {
            assert(e is InvalidIdException)
        }
    }
}
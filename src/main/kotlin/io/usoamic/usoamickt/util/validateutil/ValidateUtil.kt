package io.usoamic.usoamickt.util.validateutil

import io.usoamic.usoamickt.exception.*
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import java.math.BigDecimal
import java.math.BigInteger

class ValidateUtil {
    companion object {
        fun validatePassword(password: String) = apply {
            validateThatNotEmpty(
                password,
                EmptyPasswordException()
            )
        }

        fun validateDescription(description: String) = apply {
            validateThatNotEmpty(
                description,
                EmptyDescriptionException()
            )
        }

        fun validateNoteContent(content: String) = apply {
            validateThatNotEmpty(
                content,
                EmptyNoteContentException()
            )
        }

        fun validateComment(comment: String) = apply {
            validateThatNotEmpty(
                comment,
                EmptyCommentException()
            )
        }

        fun validatePrivateKey(privateKey: String) = apply {
            validateThatNotEmpty(
                privateKey,
                EmptyPrivateKeyException()
            )
            if (!WalletUtils.isValidPrivateKey(privateKey)) {
                throw InvalidPrivateKeyException()
            }
        }

        fun validateMnemonicPhrase(mnemonicPhrase: String) = apply {
            validateThatNotEmpty(
                mnemonicPhrase,
                EmptyMnemonicPhraseException()
            )
            if (!MnemonicUtils.validateMnemonic(mnemonicPhrase)) {
                throw InvalidMnemonicPhraseException()

            }
        }

        fun validateAddress(address: String) = apply {
            validateThatNotEmpty(
                address,
                EmptyAddressException()
            )
            if (!WalletUtils.isValidAddress(address)) {
                throw InvalidAddressException()
            }
        }

        fun validateTransferValue(value: String) = apply {
            val decimalVal = value.toBigDecimalOrNull() ?: throw EmptyValueException()
            if (decimalVal <= BigDecimal.ZERO) {
                throw InvalidValueException()
            }
        }

        fun validateAppId(appId: String) = apply {
            validateThatNotEmpty(
                appId,
                EmptyAppIdException()
            )
        }

        fun validatePurchaseId(purchaseId: String) = apply {
            validateThatNotEmpty(
                purchaseId,
                EmptyPurchaseId()
            )
        }

        fun validateId(id: String) = apply {
            val intId = id.toBigIntegerOrNull() ?: throw InvalidIdException()
            if (intId < BigInteger.ZERO) {
                throw InvalidIdException()
            }
        }

        fun validateIds(vararg ids: String) = apply {
            for (id in ids) {
                validateId(id)
            }
        }

        private fun validateThatNotEmpty(str: String, e: ValidateUtilException) {
            if (str.isEmpty()) {
                throw e
            }
        }
    }
}
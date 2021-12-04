package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.model.Account
import io.usoamic.usoamickt.util.AccountUtils
import io.usoamic.usoamickt.util.Files
import io.usoamic.usoamickt.util.Timestamp
import io.usoamic.validateutilkt.error.InvalidMnemonicPhraseError
import io.usoamic.validateutilkt.error.InvalidPrivateKeyError
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Hash.sha256
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import java.io.File

open class AccountManager(
    private val fileName: String,
    private val filePath: String
) {
    fun importPrivateKey(password: String, privateKey: String): String {
        if (!WalletUtils.isValidPrivateKey(privateKey)) {
            throw InvalidPrivateKeyError()
        }
        val credentials = Credentials.create(privateKey)
        return import(password, credentials.ecKeyPair)
    }

    fun importMnemonic(password: String, mnemonic: String): String {
        if (!MnemonicUtils.validateMnemonic(mnemonic)) {
            throw InvalidMnemonicPhraseError()
        }

        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val keyPair = ECKeyPair.create(sha256(seed))
        return import(password, keyPair)
    }

    private fun import(password: String, keyPair: ECKeyPair): String {
        val credentials = Credentials.create(keyPair)

        val walletDir = File(filePath)
        if (!walletDir.exists()) {
            walletDir.mkdir()
        }

        val accountFile = AccountUtils.initFile(filePath, fileName)

        val account = if (accountFile.exists()) {
            readAccount(
                accountFile = accountFile
            )
        } else {
            generateAccount(
                address = credentials.address,
                password = password,
                keyPair = keyPair,
                walletDir = walletDir,
                accountFile = accountFile
            )
        }

        return account.name
    }

    private fun readAccount(
        accountFile: File
    ): Account {
        return Account.parse(
            json = Files.readString(accountFile)
        )
    }

    private fun generateAccount(
        address: String,
        password: String,
        keyPair: ECKeyPair,
        walletDir: File,
        accountFile: File
    ): Account {
        val account = Account(
            address = address,
            path = walletDir.path,
            name = WalletUtils.generateWalletFile(password, keyPair, walletDir, false),
            timestamp = Timestamp.CURRENT
        )

        Files.writeString(
            file = accountFile,
            str = account.toJson()
        )

        return account
    }
}
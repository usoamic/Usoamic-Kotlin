package io.usoamic.usoamickt.core

import com.google.gson.Gson
import io.usoamic.usoamickt.model.Account
import io.usoamic.usoamickt.util.Timestamp
import io.usoamic.validateutilkt.error.InvalidMnemonicPhraseError
import io.usoamic.validateutilkt.error.InvalidPrivateKeyError
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Hash.sha256
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.FileWriter

open class AccountManager(private val fileName: String, private val filePath: String) {
    fun importPrivateKey(password: String, privateKey: String): String {
        if(!WalletUtils.isValidPrivateKey(privateKey)) {
            throw InvalidPrivateKeyError()
        }
        val credentials = Credentials.create(privateKey)
        return import(password, credentials.ecKeyPair)
    }

    fun importMnemonic(password: String, mnemonic: String): String {
        if(!MnemonicUtils.validateMnemonic(mnemonic)) {
            throw InvalidMnemonicPhraseError()
        }

        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val keyPair = ECKeyPair.create(sha256(seed))
        return import(password, keyPair)
    }

    private fun import(password: String, keyPair: ECKeyPair): String {
        val credentials = Credentials.create(keyPair)

        val directory = File(filePath)
        if(!directory.exists()) {
            directory.mkdir()
        }
        val walletFileName = WalletUtils.generateWalletFile(password, keyPair, directory, false)
        val account = Account(credentials.address, directory.path, walletFileName, Timestamp.CURRENT)

        FileWriter("$filePath${File.separator}$fileName").use {
            Gson().toJson(account, it)
        }

        return walletFileName
    }
}
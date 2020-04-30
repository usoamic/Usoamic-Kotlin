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
import java.io.IOException

open class AccountManager(private val filename: String) {
    @Throws(InvalidPrivateKeyError::class, IOException::class)
    fun importPrivateKey(password: String, privateKey: String, path: String = ""): String {
        if(!WalletUtils.isValidPrivateKey(privateKey)) {
            throw InvalidPrivateKeyError()
        }
        val credentials = Credentials.create(privateKey)
        return import(password, credentials.ecKeyPair, path)
    }

    @Throws(InvalidMnemonicPhraseError::class, IOException::class)
    fun importMnemonic(password: String, mnemonic: String, path: String = ""): String {
        if(!MnemonicUtils.validateMnemonic(mnemonic)) {
            throw InvalidMnemonicPhraseError()
        }

        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val keyPair = ECKeyPair.create(sha256(seed))
        return import(password, keyPair, path)
    }

    @Throws(Exception::class)
    private fun import(password: String, keyPair: ECKeyPair, path: String): String {
        val credentials = Credentials.create(keyPair)

        val directory = File(if(path.isEmpty()) WalletUtils.getDefaultKeyDirectory() else path)
        if(!directory.exists()) {
            directory.mkdir()
        }
        val walletFileName = WalletUtils.generateWalletFile(password, keyPair, directory, false)
        val account = Account(credentials.address, directory.path, walletFileName, Timestamp.CURRENT)

        FileWriter(if(path.isEmpty()) filename else "$path${File.separator}$filename").use {
            Gson().toJson(account, it)
        }

        return walletFileName
    }
}
package io.usoamic.cli.core

import com.google.gson.Gson
import io.usoamic.cli.exception.InvalidMnemonicPhraseException
import io.usoamic.cli.exception.InvalidPrivateKeyException
import io.usoamic.cli.model.Account
import io.usoamic.cli.util.Timestamp
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Hash.sha256
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

open class AccountManager(private val filename: String) {
    @Throws(InvalidPrivateKeyException::class, IOException::class)
    fun importPrivateKey(password: String, privateKey: String): String {
        if(WalletUtils.isValidPrivateKey(privateKey)) {
            throw InvalidPrivateKeyException()
        }
        val credentials = Credentials.create(privateKey)
        return import(password, credentials.ecKeyPair)
    }

    @Throws(InvalidMnemonicPhraseException::class, IOException::class)
    fun importMnemonic(password: String, mnemonic: String): String {
        if(!MnemonicUtils.validateMnemonic(mnemonic)) {
            throw InvalidMnemonicPhraseException()
        }

        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val keyPair = ECKeyPair.create(sha256(seed))
        return import(password, keyPair)
    }

    @Throws(Exception::class)
    private fun import(password: String, keyPair: ECKeyPair): String {
        val credentials = Credentials.create(keyPair)

        val directory = File("/keystore")
        if(!directory.exists()) {
            directory.mkdir()
        }
        val walletFileName = WalletUtils.generateWalletFile(password, keyPair, directory, false)
        val account = Account(credentials.address, directory.path, walletFileName, Timestamp.CURRENT)

        FileWriter(filename).use {
            Gson().toJson(account, it)
        }

        return walletFileName
    }
}
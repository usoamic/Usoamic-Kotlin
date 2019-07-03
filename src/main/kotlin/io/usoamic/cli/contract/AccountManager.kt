package io.usoamic.cli.contract

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.usoamic.cli.exception.InvalidMnemonicPhrase
import io.usoamic.cli.model.Account
import io.usoamic.cli.other.Config
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
    @Throws(InvalidMnemonicPhrase::class, IOException::class)
    fun import(password: String, mnemonic: String): String {
        if(!MnemonicUtils.validateMnemonic(mnemonic)) {
            throw InvalidMnemonicPhrase()
        }
        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val keyPair = ECKeyPair.create(sha256(seed))
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
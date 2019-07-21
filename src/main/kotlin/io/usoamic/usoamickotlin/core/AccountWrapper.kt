package io.usoamic.usoamickotlin.core

import io.usoamic.usoamickotlin.model.Account
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

open class AccountWrapper(private val filename: String, node: String) : AccountManager(filename) {
    protected val web3j: Web3j = Web3j.build(HttpService(node))
    private lateinit var _account: Account
    private val account: Account get() {
        if (!::_account.isInitialized) {
            val json = Files.readString(Path.of(filename))
            _account = Account.fromJson(json)
        }
        return _account
    }

    val hasAccount: Boolean get() = ::_account.isInitialized
    val address: String get() = account.address

    @Throws(Exception::class)
    fun getBalance(): BigInteger {
        return getBalance(address)
    }

    @Throws(java.lang.Exception::class)
    fun getBalance(address: String): BigInteger {
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }

    @Throws(IOException::class, CipherException::class)
    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + "/" + account.name)
    }
}
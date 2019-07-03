package io.usoamic.cli.contract

import io.usoamic.cli.model.Account
import io.usoamic.cli.other.Config
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

open class AccountWrapper(private val filename: String) : AccountManager(filename) {
    protected val web3j: Web3j = Web3j.build(HttpService(Config.NODE))
    lateinit var _account: Account
    public val account: Account get() {
        if (!::_account.isInitialized) {
            val json = Files.readString(Path.of(filename))
            _account = Account.fromJson(json)
        }
        return _account
    }

    @Throws(Exception::class)
    protected fun getBalance(): BigInteger {
        return web3j.ethGetBalance(account.address, DefaultBlockParameterName.LATEST).send().balance
    }

    @Throws(IOException::class, CipherException::class)
    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + "/" + account.name)
    }
}
package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.model.Account
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import org.web3j.utils.Files
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.math.BigDecimal
import java.math.BigInteger

open class AccountWrapper(private val fileName: String, private val filePath: String, node: String) : AccountManager(fileName, filePath) {
    protected val web3j: Web3j = Web3j.build(HttpService(node))
    private lateinit var _account: Account
    private val account: Account get() {
        if (!::_account.isInitialized) {
            val file = Account.initFile(filePath, fileName)
            val json = Files.readString(file)
            _account = Account.fromJson(json)
        }
        return _account
    }

    val hasAccount: Boolean get() {
        return try {
            address.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    val address: String get() = account.address

    fun getAddress(password: String): String {
        return getCredentials(password).address
    }

    fun removeWallet(): Boolean {
        if(!account.file.delete()) {
            throw FileNotFoundException()
        }
        return true
    }

    @Throws(Exception::class)
    fun getEthBalance(): BigInteger {
        return getEthBalance(address)
    }

    @Throws(java.lang.Exception::class)
    fun getEthBalance(address: String): BigInteger {
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }

    @Throws(Exception::class)
    fun getConvertedBalance(unit: Convert.Unit = Convert.Unit.WEI): BigDecimal {
        return Convert.fromWei(getEthBalance().toString(), unit)
    }

    @Throws(IOException::class, CipherException::class)
    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + File.separator + account.name)
    }
}
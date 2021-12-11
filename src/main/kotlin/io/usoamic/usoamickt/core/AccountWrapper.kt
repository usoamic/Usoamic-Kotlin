package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.model.Account
import io.usoamic.usoamickt.util.AccountUtils
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.utils.Convert
import org.web3j.utils.Files
import java.io.File
import java.io.FileNotFoundException
import java.math.BigDecimal
import java.math.BigInteger

open class AccountWrapper(private val fileName: String, private val filePath: String, node: String) :
    EthereumCore(fileName, filePath, node) {
    private var _account: Account? = null
    private val accountFile get() = AccountUtils.initFile(filePath, fileName)
    private val account: Account
        get() {
            if (_account == null) {
                val json = Files.readString(accountFile)
                _account = Account.parse(json)
            }
            return _account as Account
        }

    val hasAccount: Boolean
        get() {
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
        if (!account.file.delete() || !accountFile.delete()) {
            throw FileNotFoundException()
        }
        _account = null
        return true
    }

    fun getEthBalance(): BigInteger {
        return getEthBalance(address)
    }

    fun getConvertedBalance(unit: Convert.Unit = Convert.Unit.ETHER): BigDecimal {
        return Convert.fromWei(getEthBalance().toString(), unit)
    }

    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + File.separator + account.name)
    }
}
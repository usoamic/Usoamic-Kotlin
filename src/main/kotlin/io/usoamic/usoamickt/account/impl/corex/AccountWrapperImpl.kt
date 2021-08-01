package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.model.Account
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.utils.Files
import java.io.File
import java.io.FileNotFoundException

open class AccountWrapperImpl(
    private val fileName: String,
    private val filePath: String
) : AccountManagerImpl(
    fileName = fileName,
    filePath = filePath,
) {
    private var _account: Account? = null
    private val accountFile get() = Account.initFile(filePath, fileName)
    private val account: Account
        get() {
            if (_account == null) {
                val json = Files.readString(accountFile)
                _account = Account.fromJson(json)
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

    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + File.separator + account.name)
    }
}
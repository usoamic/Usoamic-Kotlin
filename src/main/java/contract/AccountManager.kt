package contract

import model.Account
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import other.Config
import java.io.IOException
import java.math.BigInteger
import javax.inject.Inject

open class AccountManager {
    @Inject
    lateinit var account: Account

    protected val web3j: Web3j = Web3j.build(HttpService(Config.NODE))

    @Throws(Exception::class)
    protected fun getBalance(): BigInteger {
        return web3j.ethGetBalance(account.address, DefaultBlockParameterName.LATEST).send().balance
    }

    @Throws(IOException::class, CipherException::class)
    protected fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, account.path + "/" + account.name)
    }
}
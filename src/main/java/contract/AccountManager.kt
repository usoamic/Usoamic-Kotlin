package contract

import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import java.math.BigInteger

open class AccountManager(private val web3j: Web3j, private val address: String) {
    @Throws(Exception::class)
    fun getBalance(): BigInteger {
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }
}
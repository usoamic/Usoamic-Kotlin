package contract

import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import other.Config
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.crypto.*
import org.web3j.protocol.core.methods.request.Transaction
import other.Config.Companion.CONTRACT_ADDRESS
import java.io.IOException
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.utils.Numeric
import org.web3j.protocol.core.methods.response.EthEstimateGas
import org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE
import org.web3j.protocol.core.methods.response.EthGetTransactionCount
import java.math.BigInteger

class TransactionManager(private val web3j: Web3j, private val address: String) : AccountManager(web3j, address) {
//    private val web3j: Web3j = Web3j.build(HttpService(Config.NODE))

    @Throws(Exception::class)
    fun executeCall(function: Function): MutableList<Type<Any>>? {
        val encodedFunction = FunctionEncoder.encode(function)

        val ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction(address, CONTRACT_ADDRESS, encodedFunction),
            DefaultBlockParameterName.LATEST
        ).send()

        return FunctionReturnDecoder.decode(ethCall.value, function.outputParameters);
    }

    /*
    @Throws(IOException::class)
    protected fun <T : Type<Any>> executeCallSingleValueReturn(function: Function): T? {
        val values = executeCall(function)
        return if(values.isEmpty()) values[0] else null
    }
    */

    @Throws(Exception::class)
    private fun executeTransaction(password: String, function: Function): String {
        val credentials = getCredentials(password)
        val nonce = getNonce(credentials.address)
        val encodedFunction = FunctionEncoder.encode(function)

        val transaction = Transaction.createEthCallTransaction(
            credentials.address,
            CONTRACT_ADDRESS.toLowerCase(),
            encodedFunction
        )

        val estimateGas = web3j.ethEstimateGas(transaction).send()

        val rawTransaction = RawTransaction.createTransaction(
            nonce,
            GAS_PRICE,
            estimateGas.amountUsed,
            CONTRACT_ADDRESS,
            encodedFunction
        )

        val signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
        val hexValue = Numeric.toHexString(signedMessage)

        val transactionResponse = web3j.ethSendRawTransaction(hexValue).sendAsync().get()
        return transactionResponse.transactionHash
    }

    @Throws(Exception::class)
    private fun getNonce(address: String): BigInteger {
        val ethGetTransactionCount =
            web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get()
        return ethGetTransactionCount.transactionCount
    }

    @Throws(IOException::class, CipherException::class)
    private fun getCredentials(password: String): Credentials {
        return WalletUtils.loadCredentials(password, ".ethereum/wallet.json")
    }
}
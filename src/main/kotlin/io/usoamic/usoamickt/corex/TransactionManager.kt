package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.util.DefaultGasProvider
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Transfer.GAS_LIMIT
import org.web3j.utils.Numeric
import java.math.BigInteger

open class TransactionManager(
    fileName: String,
    filePath: String,
    private val contractAddress: String,
    node: String
) : AccountWrapper(
    fileName = fileName,
    filePath = filePath,
    node = node
) {
    protected fun <T : Any?> executeCallSingleValueReturn(function: Function): T? {
        val values = executeCall(function)
        return if (values.isNotEmpty()) (values[0].value as T) else null
    }

    protected fun <T : Any?> executeCallEmptyPassValueAndSingleValueReturn(
        name: String,
        outputParameters: List<TypeReference<*>>
    ): T? {
        val function = Function(
            name,
            emptyList(),
            outputParameters
        )
        return executeCallSingleValueReturn(function)
    }

    protected fun <T : Any?> executeCallEmptyPassValueAndUint256Return(name: String): T? =
        executeCallUint256ValueReturn(name, emptyList())

    protected fun <T : Any?> executeCallUint256ValueReturn(name: String, inputParameters: List<Type<out Any>>): T? {
        val function = Function(
            name,
            inputParameters,
            listOf(object : TypeReference<Uint256>() {})
        )
        return executeCallSingleValueReturn(function)
    }

    protected fun executeCall(function: Function): MutableList<Type<Any>> {
        val encodedFunction = FunctionEncoder.encode(function)

        val ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction(address, contractAddress, encodedFunction),
            DefaultBlockParameterName.LATEST
        ).send()

        return FunctionReturnDecoder.decode(ethCall.value, function.outputParameters)
    }

    protected fun executeTransaction(password: String, name: String, inputParameters: List<Type<out Any>>, txSpeed: TxSpeed): String {
        val function = Function(
            name,
            inputParameters,
            emptyList()
        )
        return executeTransactionFunctionPassValue(password, function, txSpeed)
    }

    protected fun executeTransactionFunctionPassValue(password: String, function: Function, txSpeed: TxSpeed): String {
        val credentials = getCredentials(password)
        val nonce = getNonce(credentials.address)
        val encodedFunction = FunctionEncoder.encode(function)

        val transaction = Transaction.createEthCallTransaction(
            credentials.address,
            contractAddress,
            encodedFunction
        )

        val estimateGas = web3j.ethEstimateGas(transaction).send()

        val rawTransaction = RawTransaction.createTransaction(
            nonce,
            getGasPrice(txSpeed),
            estimateGas.amountUsed,
            contractAddress,
            encodedFunction
        )

        return sendTransaction(rawTransaction, credentials)
    }

    fun transferEth(password: String, to: String, value: BigInteger, txSpeed: TxSpeed): String {
        val credentials = getCredentials(password)
        val nonce = getNonce(credentials.address)

        val rawTransaction = RawTransaction.createEtherTransaction(
            nonce,
            getGasPrice(txSpeed),
            GAS_LIMIT,
            to,
            value
        )

        return sendTransaction(rawTransaction, credentials)
    }

    fun waitTransactionReceipt(txHash: String, callback: (receipt: TransactionReceipt) -> Unit) {
        while (true) {
            val transactionReceipt = web3j.ethGetTransactionReceipt(txHash).send()
            val result = transactionReceipt.result

            if (result != null) {
                callback(result)
                break
            }

            Thread.sleep(15000)
        }

    }

    fun getGasPrice(): BigInteger {
        return web3j.ethGasPrice().send().gasPrice
    }

    fun getGasPrice(txSpeed: TxSpeed): BigInteger {
        return when(txSpeed) {
            TxSpeed.Auto -> getGasPrice()
            TxSpeed.GP20 -> DefaultGasProvider.GAS_PRICE_20
            TxSpeed.GP40 -> DefaultGasProvider.GAS_PRICE_40
            TxSpeed.GP60 -> DefaultGasProvider.GAS_PRICE_60
            TxSpeed.GP80 -> DefaultGasProvider.GAS_PRICE_80
            TxSpeed.GP100 -> DefaultGasProvider.GAS_PRICE_100
            TxSpeed.GP120 -> DefaultGasProvider.GAS_PRICE_120
        }
    }

    private fun sendTransaction(rawTransaction: RawTransaction, credentials: Credentials): String {
        val signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
        val hexValue = Numeric.toHexString(signedMessage)

        val transactionResponse = web3j.ethSendRawTransaction(hexValue).sendAsync().get()
        return transactionResponse.transactionHash
    }

    private fun getNonce(address: String): BigInteger {
        val ethGetTransactionCount =
            web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get()
        return ethGetTransactionCount.transactionCount
    }
}
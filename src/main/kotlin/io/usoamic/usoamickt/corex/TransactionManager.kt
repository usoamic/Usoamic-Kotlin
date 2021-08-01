package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.extensions.encode
import io.usoamic.usoamickt.util.DefaultGasProvider
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
    private val contractAddress: String,
    node: String
) : EthereumCore(
    node = node
) {
    val gasPrice: BigInteger get() = web3j.ethGasPrice().send().gasPrice

    protected fun <T : Any?> executeCallSingleValueReturn(
        addressOfRequester: String,
        function: Function
    ): T? {
        val values = executeCall(
            addressOfRequester = addressOfRequester,
            function = function
        )
        return if (values.isNotEmpty()) (values[0].value as T) else null
    }

    protected fun <T : Any?> executeCallEmptyPassValueAndSingleValueReturn(
        addressOfRequester: String,
        name: String,
        outputParameters: List<TypeReference<*>>
    ): T? = executeCallSingleValueReturn(
        addressOfRequester = addressOfRequester,
        function = Function(
            name,
            emptyList(),
            outputParameters
        )
    )

    protected fun <T : Any?> executeCallEmptyPassValueAndUint256Return(
        addressOfRequester: String,
        name: String
    ): T? = executeCallUint256ValueReturn(
        name = name,
        addressOfRequester = addressOfRequester,
        inputParameters = emptyList()
    )

    protected fun <T : Any?> executeCallUint256ValueReturn(
        addressOfRequester: String,
        name: String,
        inputParameters: List<Type<out Any>>
    ): T? = executeCallSingleValueReturn(
        addressOfRequester = addressOfRequester,
        function = Function(
            name,
            inputParameters,
            listOf(object : TypeReference<Uint256>() {})
        )
    )

    protected fun executeCall(
        addressOfRequester: String,
        function: Function
    ): MutableList<Type<Any>> {
        val encodedFunction = function.encode()

        val ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction(addressOfRequester, contractAddress, encodedFunction),
            DefaultBlockParameterName.LATEST
        ).send()

        return FunctionReturnDecoder.decode(ethCall.value, function.outputParameters)
    }

    protected fun executeTransaction(
        credentials: Credentials,
        name: String,
        inputParameters: List<Type<out Any>>,
        txSpeed: TxSpeed
    ): String = executeTransactionFunctionPassValue(
        credentials = credentials,
        function = Function(
            name,
            inputParameters,
            emptyList()
        ),
        txSpeed = txSpeed
    )

    protected fun executeTransactionFunctionPassValue(
        credentials: Credentials,
        function: Function,
        txSpeed: TxSpeed
    ): String {
        val nonce = getNonce(credentials)
        val encodedFunction = function.encode()

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

    fun transferEth(
        credentials: Credentials,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        val nonce = getNonce(credentials)

        val rawTransaction = RawTransaction.createEtherTransaction(
            nonce,
            getGasPrice(txSpeed),
            GAS_LIMIT,
            to,
            value
        )

        return sendTransaction(rawTransaction, credentials)
    }

    fun waitTransactionReceipt(
        txHash: String,
        callback: (receipt: TransactionReceipt) -> Unit
    ) {
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

    fun getGasPrice(txSpeed: TxSpeed): BigInteger = when (txSpeed) {
        TxSpeed.Auto -> gasPrice
        TxSpeed.GP20 -> DefaultGasProvider.GAS_PRICE_20
        TxSpeed.GP40 -> DefaultGasProvider.GAS_PRICE_40
        TxSpeed.GP60 -> DefaultGasProvider.GAS_PRICE_60
        TxSpeed.GP80 -> DefaultGasProvider.GAS_PRICE_80
        TxSpeed.GP100 -> DefaultGasProvider.GAS_PRICE_100
        TxSpeed.GP120 -> DefaultGasProvider.GAS_PRICE_120
    }

    private fun sendTransaction(
        rawTransaction: RawTransaction,
        credentials: Credentials
    ): String {
        val signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
        val hexValue = Numeric.toHexString(signedMessage)

        val transactionResponse = web3j.ethSendRawTransaction(hexValue).sendAsync().get()
        return transactionResponse.transactionHash
    }

    private fun getNonce(credentials: Credentials): BigInteger {
        val ethGetTransactionCount =
            web3j.ethGetTransactionCount(credentials.address, DefaultBlockParameterName.LATEST).sendAsync().get()
        return ethGetTransactionCount.transactionCount
    }
}
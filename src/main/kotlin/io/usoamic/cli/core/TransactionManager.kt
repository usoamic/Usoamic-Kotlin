package io.usoamic.cli.core

import io.usoamic.cli.other.Config.Companion.CONTRACT_ADDRESS
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE
import org.web3j.utils.Numeric
import java.math.BigInteger

open class TransactionManager(filename: String, node: String) : AccountWrapper(filename, node) {
    @Throws(Exception::class)
    protected fun <T : Any?>executeCallSingleValueReturn(function: Function): T? {
        val values = executeCall(function)
        return if(values.isNotEmpty()) (values[0].value as T) else null
    }

    @Throws(Exception::class)
    protected fun <T : Any?>executeCallEmptyPassValueAndSingleValueReturn(name: String, outputParameters: List<TypeReference<*>>): T? {
        val function = Function(
            name,
            emptyList(),
            outputParameters
        )
        return executeCallSingleValueReturn(function)
    }

    @Throws(Exception::class)
    protected fun <T : Any?>executeCallEmptyPassValueAndUint256Return(name: String): T? = executeCallUint256ValueReturn(name, emptyList())

    @Throws(Exception::class)
    protected fun <T : Any?>executeCallUint256ValueReturn(name: String, inputParameters: List<Type<out Any>>): T? {
        val function = Function(
            name,
            inputParameters,
            listOf(object: TypeReference<Uint256>() {})
        )
        return executeCallSingleValueReturn(function)
    }

    @Throws(Exception::class)
    protected fun executeCall(function: Function): MutableList<Type<Any>> {
        val encodedFunction = FunctionEncoder.encode(function)

        val ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction(account.address, CONTRACT_ADDRESS, encodedFunction),
            DefaultBlockParameterName.LATEST
        ).send()

        return FunctionReturnDecoder.decode(ethCall.value, function.outputParameters)
    }

    @Throws(Exception::class)
    protected fun executeTransaction(password: String, name: String, inputParameters: List<Type<out Any>>): String {
        val function = Function(
            name,
            inputParameters,
            emptyList()
        )
        return executeTransactionFunctionPassValue(password, function)
    }

    @Throws(Exception::class)
    protected fun executeTransactionFunctionPassValue(password: String, function: Function): String {
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
        val ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get()
        return ethGetTransactionCount.transactionCount
    }
}
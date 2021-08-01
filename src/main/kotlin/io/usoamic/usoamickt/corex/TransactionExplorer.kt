package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.model.Transaction
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

open class TransactionExplorer(
    contractAddress: String,
    node: String
) : Purchases(
    contractAddress = contractAddress,
    node = node
) {
    fun getTransaction(addressOfRequester: String, txId: BigInteger): Transaction {
        return prepareTransaction(
            addressOfRequester = addressOfRequester,
            function = Function(
                "getTransaction",
                listOf(Uint256(txId)),
                listOf(
                    object : TypeReference<Bool>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Uint256>() {}
                )
            )
        )
    }

    fun getTransactionByAddress(owner: String, txId: BigInteger): Transaction {
        return prepareTransaction(
            addressOfRequester = owner,
            function = Function(
                "getTransactionByAddress",
                listOf(
                    Address(owner),
                    Uint256(txId)
                ),
                listOf(
                    object : TypeReference<Bool>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Uint256>() {}
                )
            )
        )
    }

    fun getNumberOfTransactions(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        name = "getNumberOfTransactions",
        addressOfRequester = addressOfRequester
    )

    fun getNumberOfTransactionsByAddress(owner: String): BigInteger? = executeCallUint256ValueReturn(
        name = "getNumberOfTransactionsByAddress",
        addressOfRequester = owner,
        inputParameters = listOf(Address(owner))
    )

    private fun prepareTransaction(
        addressOfRequester: String,
        function: Function
    ): Transaction {
        val result = executeCall(
            addressOfRequester = addressOfRequester,
            function = function
        )
        return Transaction.Builder()
            .setExist(result[0].value as Boolean)
            .setTxId(result[1].value as BigInteger)
            .setFrom(result[2].value as String)
            .setTo(result[3].value as String)
            .setValue(result[4].value as BigInteger)
            .setTimestamp(result[5].value as BigInteger)
            .build()
    }
}
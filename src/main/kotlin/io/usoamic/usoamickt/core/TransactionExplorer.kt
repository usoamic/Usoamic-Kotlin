package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.model.Transaction
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

@Deprecated(CoreDeprecatedData.message)
open class TransactionExplorer constructor(fileName: String, filePath: String, contractAddress: String, node: String) : Purchases(fileName, filePath, contractAddress, node) {
    fun getTransaction(txId: BigInteger): Transaction {
        val function = Function(
            "getTransaction",
            listOf(Uint256(txId)),
            listOf(
                object: TypeReference<Bool>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Address>() {},
                object: TypeReference<Address>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Uint256>() {}
            )
        )
        return prepareTransaction(function)
    }

    fun getTransactionByAddress(owner: String, txId: BigInteger): Transaction {
        val function = Function(
            "getTransactionByAddress",
            listOf(
                Address(owner),
                Uint256(txId)
            ),
            listOf(
                object: TypeReference<Bool>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Address>() {},
                object: TypeReference<Address>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Uint256>() {}
            )
        )
        return prepareTransaction(function)
    }

    fun getNumberOfTransactions(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getNumberOfTransactions")

    fun getNumberOfTransactionsByAddress(owner: String): BigInteger? = executeCallUint256ValueReturn("getNumberOfTransactionsByAddress", listOf(Address(owner)))

    private fun prepareTransaction(function: Function): Transaction {
        val result = executeCall(function)
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
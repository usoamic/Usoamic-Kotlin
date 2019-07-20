package io.usoamic.usoamickotlin.core

import io.usoamic.usoamickotlin.model.Transaction
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

// bool exist, uint256 txId, address from, address to, uint256 value, uint256 timestamp

open class TransactionExplorer constructor(filename: String, contractAddress: String, node: String) : Purchases(filename, contractAddress, node) {
    @Throws(Exception::class)
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

    @Throws(Exception::class)
    fun getNumberOfTransactions(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getNumberOfTransactions")

    @Throws(Exception::class)
    fun getNumberOfTransactionsByAddress(owner: String): BigInteger? = executeCallUint256ValueReturn("getNumberOfTransactionsByAddress", listOf(Address(owner)))
}
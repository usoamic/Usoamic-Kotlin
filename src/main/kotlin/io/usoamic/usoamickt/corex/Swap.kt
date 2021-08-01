package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

open class Swap(
    fileName: String,
    filePath: String,
    contractAddress: String,
    node: String
) : TransactionExplorer(
    fileName = fileName,
    filePath = filePath,
    contractAddress = contractAddress,
    node = node
) {
    fun withdrawEth(password: String, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        password,
        "withdrawEth",
        listOf(Uint256(value)),
        txSpeed
    )

    fun burnSwap(password: String, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        password,
        "burnSwap",
        listOf(Uint256(value)),
        txSpeed
    )

    fun setSwapRate(password: String, swapRate: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        password,
        "setSwapRate",
        listOf(Uint256(swapRate)),
        txSpeed
    )

    fun setSwappable(password: String, swappable: Boolean, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        password,
        "setSwappable",
        listOf(Bool(swappable)),
        txSpeed
    )

    fun getSwapBalance(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapBalance")

    fun getSwapRate(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapRate")

    fun getSwappable(): Boolean? = executeCallEmptyPassValueAndSingleValueReturn(
        "getSwappable",
        listOf(object : TypeReference<Bool>() {})
    )
}
package io.usoamic.usoamickt.core

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

open class Swap constructor(fileName: String, filePath: String, contractAddress: String, node: String) : TransactionExplorer(fileName, filePath, contractAddress, node) {
    fun withdrawEth(password: String, value: BigInteger): String = executeTransaction(
            password,
            "withdrawEth",
            listOf(Uint256(value))
    )

    fun burnSwap(password: String, value: BigInteger): String = executeTransaction(
            password,
            "burnSwap",
            listOf(Uint256(value))
    )

    fun setSwapRate(password: String, swapRate: BigInteger): String = executeTransaction(
        password,
        "setSwapRate",
        listOf(Uint256(swapRate))
    )

    fun setSwappable(password: String, swappable: Boolean): String = executeTransaction(
        password,
        "setSwappable",
        listOf(Bool(swappable))
    )

    fun getSwapBalance(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapBalance")

    fun getSwapRate(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapRate")

    fun getSwappable(): Boolean? = executeCallEmptyPassValueAndSingleValueReturn(
        "getSwappable",
        listOf(object: TypeReference<Bool>() { })
    )
}
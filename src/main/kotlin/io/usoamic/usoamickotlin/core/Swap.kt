package io.usoamic.usoamickotlin.core

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

open class Swap constructor(filename: String, contractAddress: String, node: String) : TransactionExplorer(filename, contractAddress, node) {
    @Throws(Exception::class)
    fun withdrawEth(password: String, value: BigInteger): String = executeTransaction(
            password,
            "withdrawEth",
            listOf(Uint256(value))
    )

    @Throws(Exception::class)
    fun burnSwap(password: String, value: BigInteger): String = executeTransaction(
            password,
            "burnSwap",
            listOf(Uint256(value))
    )

    @Throws(Exception::class)
    fun setSwapRate(password: String, swapRate: BigInteger): String = executeTransaction(
        password,
        "setSwapRate",
        listOf(Uint256(swapRate))
    )

    @Throws(Exception::class)
    fun setSwappable(password: String, swappable: Boolean): String = executeTransaction(
        password,
        "setSwappable",
        listOf(Bool(swappable))
    )

    @Throws(Exception::class)
    fun getSwapBalance(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapBalance")

    @Throws(Exception::class)
    fun getSwapRate(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSwapRate")

    @Throws(Exception::class)
    fun getSwappable(): Boolean? = executeCallEmptyPassValueAndSingleValueReturn(
        "getSwappable",
        listOf(object: TypeReference<Bool>() { })
    )
}
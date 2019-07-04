package io.usoamic.cli.core

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

open class Swap constructor(filename: String) : TransactionManager(filename) {
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

    fun getSwapBalance(): BigInteger? {
        val function = Function(
            "getSwapBalance",
            emptyList(),
            listOf(object: TypeReference<Uint256>() {})
        )
        return executeCallSingleValueReturn(function)
    }
}
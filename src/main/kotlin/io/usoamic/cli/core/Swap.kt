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
            "withdrawEth",
            listOf(Uint256(value))
    )


}
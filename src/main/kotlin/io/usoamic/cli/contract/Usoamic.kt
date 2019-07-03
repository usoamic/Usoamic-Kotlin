package io.usoamic.cli.contract

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String


class Usoamic constructor(filename: String) : TransactionManager(filename) {
    init {

    }

    fun balanceOf(address: String): BigInteger? {
        val function = Function(
            "balanceOf",
            listOf(Utf8String(address)),
            listOf(object: TypeReference<Uint256>() { })
        )
        val result = executeCallSingleValueReturn(function)
        return if(result == null) result else result as BigInteger
    }
}
package io.usoamic.cli.contract

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function


class Contract constructor(filename: String) : TransactionManager(filename) {
    init {

    }

    fun balanceOf(topicId: BigInteger): BigInteger {
        val function = Function(
            "balanceOf",
            listOf(Uint256(topicId)),
            listOf(object: TypeReference<Uint256>() { })
        )
        return executeCallSingleValueReturn(function) as BigInteger
    }
}
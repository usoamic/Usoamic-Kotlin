package io.usoamic.cli.core

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function

open class Purchases constructor(filename: String) : TransactionManager(filename) {
    fun makePurchase(password: String, appId: String, purchaseId: String, cost: BigInteger): String {
        val function = Function(
            "makePurchase",
            listOf(
                Utf8String(appId),
                Utf8String(purchaseId),
                Uint256(cost)
            ),
            listOf(
                object: TypeReference<Bool>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Utf8String>() { },
                object: TypeReference<Uint256>() { },
                object: TypeReference<Uint256>() { }
            )
        )
        return executeTransaction(password, function)
    }


}
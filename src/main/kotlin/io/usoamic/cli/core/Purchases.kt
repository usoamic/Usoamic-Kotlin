package io.usoamic.cli.core

import io.usoamic.cli.model.Purchase
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
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
            emptyList()
        )
        return executeTransaction(password, function)
    }

    fun getPurchaseByAddress(address: String, id: BigInteger): Purchase {
        val function = Function(
            "getPurchaseByAddress",
            listOf(
                Address(address),
                Uint256(id)
            ),
            listOf(
                object: TypeReference<Bool>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Utf8String>() {},
                object: TypeReference<Utf8String>() {},
                object: TypeReference<Uint256>() {},
                object: TypeReference<Uint256>() {}
            )
        )
        val result = executeCall(function)

        return Purchase.Builder()
            .setIsExist(result[0] as Boolean)
            .setId(result[1] as BigInteger)
            .setPurchaseId(result[2] as String)
            .setAppId(result[3] as String)
            .setCost(result[4] as BigInteger)
            .setTimestamp(result[5] as BigInteger)
            .build()
    }

    fun getNumberOfPurchaseByAddress(): BigInteger? {
        val function = Function(
            "getNumberOfPurchaseByAddress",
            emptyList(),
            listOf(object: TypeReference<Uint256>() { })
        )
        return executeCallSingleValueReturn(function)
    }
}
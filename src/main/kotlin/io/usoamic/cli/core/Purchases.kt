package io.usoamic.cli.core

import io.usoamic.cli.model.Purchase
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function
import java.lang.Exception

open class Purchases constructor(filename: String, node: String) : Notes(filename, node) {
    @Throws(Exception::class)
    fun makePurchase(password: String, appId: String, purchaseId: String, cost: BigInteger): String = executeTransaction(
        password,
        "makePurchase",
        listOf(
            Utf8String(appId),
            Utf8String(purchaseId),
            Uint256(cost)
        )
    )

    @Throws(Exception::class)
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

    @Throws(Exception::class)
    fun getNumberOfPurchaseByAddress(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getNumberOfPurchaseByAddress")
}
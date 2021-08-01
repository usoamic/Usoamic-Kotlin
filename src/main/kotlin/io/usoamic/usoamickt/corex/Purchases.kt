package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Purchase
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import java.math.BigInteger

open class Purchases(
    contractAddress: String,
    node: String
) : Notes(
    contractAddress = contractAddress,
    node = node
) {
    fun makePurchase(
        credentials: Credentials,
        appId: String,
        purchaseId: String,
        cost: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "makePurchase",
        inputParameters = listOf(
            Utf8String(appId),
            Utf8String(purchaseId),
            Uint256(cost)
        ),
        txSpeed = txSpeed
    )

    fun getPurchaseByAddress(address: String, id: BigInteger): Purchase {
        val result = executeCall(
            addressOfRequester = address,
            function = Function(
                "getPurchaseByAddress",
                listOf(
                    Address(address),
                    Uint256(id)
                ),
                listOf(
                    object : TypeReference<Bool>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Utf8String>() {},
                    object : TypeReference<Utf8String>() {},
                    object : TypeReference<Uint256>() {},
                    object : TypeReference<Uint256>() {}
                )
            )
        )

        return Purchase.Builder()
            .setIsExist(result[0].value as Boolean)
            .setId(result[1].value as BigInteger)
            .setPurchaseId(result[2].value as String)
            .setAppId(result[3].value as String)
            .setCost(result[4].value as BigInteger)
            .setTimestamp(result[5].value as BigInteger)
            .build()
    }

    fun getLastPurchaseId(address: String): BigInteger? {
        return getNumberOfPurchasesByAddress(address)!!.subtract(BigInteger.ONE)
    }
    fun getNumberOfPurchasesByAddress(address: String): BigInteger? = executeCallUint256ValueReturn(
        name = "getNumberOfPurchasesByAddress",
        addressOfRequester = address,
        inputParameters = listOf(Address(address))
    )
}
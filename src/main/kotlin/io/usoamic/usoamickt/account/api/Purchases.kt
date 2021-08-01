package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Purchase
import java.math.BigInteger

interface Purchases {
    fun makePurchase(
        password: String,
        appId: String,
        purchaseId: String,
        cost: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun getPurchaseByAddress(
        address: String,
        id: BigInteger
    ): Purchase

    fun getLastPurchaseId(address: String): BigInteger?

    fun getNumberOfPurchasesByAddress(address: String): BigInteger?
}
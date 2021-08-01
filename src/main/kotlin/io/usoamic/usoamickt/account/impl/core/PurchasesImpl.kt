package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.Purchases
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.model.Purchase
import java.math.BigInteger

open class PurchasesImpl constructor(
    private val usoamic: Usoamic
) : NotesImpl(usoamic),
    Purchases {
    override fun makePurchase(
        password: String,
        appId: String,
        purchaseId: String,
        cost: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.makePurchase(
            password = password,
            appId = appId,
            purchaseId = purchaseId,
            cost = cost,
            txSpeed = txSpeed
        )
    }

    override fun getPurchaseByAddress(
        address: String,
        id: BigInteger
    ): Purchase {
        return usoamic.getPurchaseByAddress(
            address = address,
            id = id
        )
    }

    override fun getLastPurchaseId(address: String): BigInteger? {
        return usoamic.getLastPurchaseId(address)
    }

    override fun getNumberOfPurchasesByAddress(address: String): BigInteger? {
        return usoamic.getNumberOfPurchasesByAddress(address)
    }

}
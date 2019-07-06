package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.util.Coin
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class PurchasesTest {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun makePurchaseTest() {
        val purchaser = usoamic.account.address

        val ownerBalance = usoamic.balanceOf(TestConfig.OWNER_ADDRESS)
        val purchaserBalance = usoamic.balanceOf(purchaser)

        val appId = "appId" + Random.nextInt()
        val purchaseId = "purchaseId" + Random.nextInt()
        val cost = Coin.ONE.toSat()
        val purchaseTxHash = usoamic.makePurchase(TestConfig.PASSWORD, appId, purchaseId,  cost)
        val numberOfPurchases = usoamic.getNumberOfPurchaseByAddress(purchaser)

        usoamic.waitTransactionReceipt(purchaseTxHash) {
            val lastPurchaseId = usoamic.getLastPurchaseId(purchaser)
            assert(numberOfPurchases!!.compareTo(lastPurchaseId) == 0)

            val purchase = usoamic.getPurchaseByAddress(purchaser, lastPurchaseId!!)
            assert(purchase.isExist)
            assert(purchase.appId == appId)
            assert(purchase.id == lastPurchaseId)
            assert(purchase.purchaseId == purchaseId)
            assert(purchase.cost.compareTo(cost) == 0)

            val newOwnerBalance = usoamic.balanceOf(TestConfig.OWNER_ADDRESS)
            val newPurchaserBalance = usoamic.balanceOf(purchaser)
            assert(newOwnerBalance!!.subtract(ownerBalance!!).compareTo(cost) == 0)
            assert(purchaserBalance!!.subtract(newPurchaserBalance!!).compareTo(cost) == 0)
        }
    }
}
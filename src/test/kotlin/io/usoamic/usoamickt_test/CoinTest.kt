package io.usoamic.usoamickt_test

import io.usoamic.usoamickt.util.Coin
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CoinTest {
    @Test
    fun coinTest() {
        testValueConversion(Coin.fromCoin(BigDecimal.TEN), BigDecimal.TEN)
        testValueConversion(Coin.fromCoin("1.2314"), BigDecimal("1.2314"))
        testValueConversion(Coin.fromCoin("1"), BigDecimal("1"))
    }

    private fun testValueConversion(coin: Coin, value: BigDecimal) {
        val coinValue = coin.toBigDecimal()
        val satValue = coin.toSat()
        val coinValueFromSat = Coin.fromSat(satValue).toBigDecimal()

        println("coinValue: $coinValue")
        println("satValue: $satValue")
        println("coinValueFromSat: $coinValueFromSat")
        println("satPerCoin: " + Coin.SAT_PER_COIN)

        assert(coinValue.compareTo(value) == 0)
        assert(coinValueFromSat.compareTo(coinValue) == 0)
    }
}
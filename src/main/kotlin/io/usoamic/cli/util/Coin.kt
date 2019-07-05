package io.usoamic.cli.util

import java.math.BigDecimal
import java.math.BigInteger

class Coin(value: BigDecimal) {
    private val value: BigDecimal

    init {
        this.value = value.setScale(DECIMALS)
    }

    companion object {
        fun fromSat(value: BigInteger): Coin = Coin(value.toBigDecimal(DECIMALS))
        fun fromCoin(value: String): Coin = Coin(BigDecimal(value))
        fun fromCoin(value: BigDecimal): Coin = Coin(value)
        public const val DECIMALS: Int = 8
        val SAT_PER_COIN: BigDecimal = BigDecimal.TEN.pow(DECIMALS)
    }

    fun toSat(): BigInteger {
        return value.multiply(SAT_PER_COIN).toBigInteger()
    }

    fun toBigDecimal(): BigDecimal {
        return value
    }
}
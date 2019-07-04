package io.usoamic.cli.util

import java.math.BigDecimal
import java.math.BigInteger

class Coin private constructor(private val value: BigDecimal){
    companion object {
        fun fromSat(value: BigInteger): Coin = Coin(value.toBigDecimal(8).divide(getSatPerCoin()))
        fun fromCoin(value: BigDecimal): Coin = Coin(value)
        private fun getSatPerCoin(): BigDecimal = BigDecimal.TEN.pow(8).setScale(8)
    }

    fun toSat(): BigInteger {
        return value.multiply(getSatPerCoin()).toBigInteger()
    }

    fun toBigDecimal(): BigDecimal {
        return value
    }
}
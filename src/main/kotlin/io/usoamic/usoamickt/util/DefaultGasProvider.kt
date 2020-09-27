package io.usoamic.usoamickt.util

import java.math.BigInteger

object DefaultGasProvider {
    val GAS_LIMIT: BigInteger = BigInteger.valueOf(9_000_000L)
    val GAS_PRICE: BigInteger  = BigInteger.valueOf(35_000_000_000L)

    val GAS_PRICE_20: BigInteger = BigInteger.valueOf(20_000_000_000L)
    val GAS_PRICE_40: BigInteger = BigInteger.valueOf(40_000_000_000L)
    val GAS_PRICE_60: BigInteger = BigInteger.valueOf(60_000_000_000L)
    val GAS_PRICE_80: BigInteger = BigInteger.valueOf(80_000_000_000L)
    val GAS_PRICE_100: BigInteger = BigInteger.valueOf(100_000_000_000L)
    val GAS_PRICE_120: BigInteger = BigInteger.valueOf(120_000_000_000L)
}
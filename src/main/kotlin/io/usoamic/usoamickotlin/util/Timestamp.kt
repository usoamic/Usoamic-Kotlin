package io.usoamic.usoamickotlin.util

import java.math.BigInteger

class Timestamp {
    companion object {
        val CURRENT: BigInteger get() = BigInteger.valueOf(System.currentTimeMillis()/1000)
    }
}
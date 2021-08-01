package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed
import java.math.BigInteger

interface Swap {
    fun withdrawEth(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun burnSwap(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun setSwapRate(
        password: String,
        swapRate: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun setSwappable(
        password: String,
        swappable: Boolean,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun getSwapBalance(): BigInteger?

    fun getSwapRate(): BigInteger?

    fun getSwappable(): Boolean?
}
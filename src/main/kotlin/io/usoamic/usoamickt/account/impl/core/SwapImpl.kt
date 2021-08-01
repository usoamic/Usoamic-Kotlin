package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.Swap
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import java.math.BigInteger

open class SwapImpl constructor(
    private val usoamic: Usoamic
) : TransactionExplorerImpl(usoamic),
    Swap {
    override fun withdrawEth(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.withdrawEth(password, value, txSpeed)
    }

    override fun burnSwap(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.burnSwap(
            password = password,
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun setSwapRate(
        password: String,
        swapRate: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setSwapRate(
            password = password,
            swapRate = swapRate,
            txSpeed = txSpeed
        )
    }

    override fun setSwappable(
        password: String,
        swappable: Boolean,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setSwappable(
            password = password,
            swappable = swappable,
            txSpeed = txSpeed
        )
    }

    override fun getSwapBalance(): BigInteger? {
        return usoamic.getSwapBalance()
    }

    override fun getSwapRate(): BigInteger? {
        return usoamic.getSwapRate()
    }

    override fun getSwappable(): Boolean? {
        return usoamic.getSwappable()
    }

}
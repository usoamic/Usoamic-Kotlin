package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.Swap
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import java.math.BigInteger

open class SwapImpl constructor(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : TransactionExplorerImpl(
    fileName = fileName,
    filePath = filePath,
    usoamic = usoamic
),
    Swap {
    override fun withdrawEth(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.withdrawEth(
            credentials = getCredentials(password),
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun burnSwap(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.burnSwap(
            credentials = getCredentials(password),
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
            credentials = getCredentials(password),
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
            credentials = getCredentials(password),
            swappable = swappable,
            txSpeed = txSpeed
        )
    }

    override fun getSwapBalance(): BigInteger? {
        return usoamic.getSwapBalance(
            addressOfRequester = address
        )
    }

    override fun getSwapRate(): BigInteger? {
        return usoamic.getSwapRate(
            addressOfRequester = address
        )
    }

    override fun getSwappable(): Boolean? {
        return usoamic.getSwappable(
            addressOfRequester = address
        )
    }

}
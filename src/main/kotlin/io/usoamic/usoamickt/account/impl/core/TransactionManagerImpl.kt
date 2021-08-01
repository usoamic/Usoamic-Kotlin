package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.TransactionManager
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigInteger

open class TransactionManagerImpl(
    private val usoamic: Usoamic
) : AccountWrapperImpl(usoamic),
    TransactionManager {
    override fun transferEth(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.transferEth(
            password = password,
            to = to,
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun waitTransactionReceipt(
        txHash: String,
        callback: (receipt: TransactionReceipt) -> Unit
    ) {
        return usoamic.waitTransactionReceipt(
            txHash = txHash,
            callback = callback
        )
    }

    override fun getGasPrice(): BigInteger {
        return usoamic.getGasPrice()
    }

    override fun getGasPrice(txSpeed: TxSpeed): BigInteger {
        return usoamic.getGasPrice(txSpeed)
    }

}
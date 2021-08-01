package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigInteger

interface TransactionManager {
    fun transferEth(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String

    fun waitTransactionReceipt(
        txHash: String,
        callback: (receipt: TransactionReceipt) -> Unit
    )

    fun getGasPrice(): BigInteger

    fun getGasPrice(
        txSpeed: TxSpeed
    ): BigInteger
}
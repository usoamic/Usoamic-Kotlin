package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.TransactionManager
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigInteger

open class TransactionManagerImpl(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : AccountWrapperImpl(
    fileName = fileName,
    filePath = filePath
), TransactionManager {
    override fun transferEth(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.transferEth(
            credentials = getCredentials(password),
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
        return usoamic.gasPrice
    }

    override fun getGasPrice(txSpeed: TxSpeed): BigInteger {
        return usoamic.getGasPrice(txSpeed)
    }

}
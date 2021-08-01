package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.EthereumCore
import io.usoamic.usoamickt.corex.Usoamic
import org.web3j.protocol.core.methods.response.EthBlock
import java.math.BigInteger

open class EthereumCoreImpl(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : AccountManagerImpl(
    fileName = fileName,
    filePath = filePath
),
    EthereumCore {
    override fun getEthBalance(address: String): BigInteger {
        return usoamic.getEthBalance(address)
    }

    override fun getEthLastBlock(): EthBlock.Block {
        return usoamic.getEthLastBlock()
    }

    override fun getEthHeight(): BigInteger {
        return usoamic.getEthHeight()
    }
}
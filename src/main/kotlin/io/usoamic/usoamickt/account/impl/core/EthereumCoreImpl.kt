package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.EthereumCore
import io.usoamic.usoamickt.core.Usoamic
import org.web3j.protocol.core.methods.response.EthBlock
import java.math.BigInteger

open class EthereumCoreImpl(
    private val usoamic: Usoamic
) : AccountManagerImpl(usoamic),
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
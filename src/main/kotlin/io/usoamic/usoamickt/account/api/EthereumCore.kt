package io.usoamic.usoamickt.account.api

import org.web3j.protocol.core.methods.response.EthBlock
import java.math.BigInteger

interface EthereumCore {
    fun getEthBalance(address: String): BigInteger

    fun getEthLastBlock(): EthBlock.Block

    fun getEthHeight(): BigInteger
}
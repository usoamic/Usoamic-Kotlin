package io.usoamic.usoamickt.corex

import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.http.HttpService
import java.math.BigInteger

open class EthereumCore(
    fileName: String,
    filePath: String,
    node: String
) : AccountManager(
    fileName = fileName,
    filePath = filePath
) {
    protected val web3j: Web3j = Web3j.build(HttpService(node))

    fun getEthBalance(address: String): BigInteger {
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }

    fun getEthLastBlock(): EthBlock.Block {
        return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().block
    }

    fun getEthHeight(): BigInteger {
        return getEthLastBlock().number
    }
}
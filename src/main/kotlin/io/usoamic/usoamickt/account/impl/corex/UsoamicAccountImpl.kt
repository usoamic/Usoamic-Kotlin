package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.UsoamicAccount
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

class UsoamicAccountImpl constructor(
    private val fileName: String,
    private val filePath: String,
    private val usoamic: Usoamic
) : SwapImpl(
    fileName = fileName,
    filePath = filePath,
    usoamic = usoamic
), UsoamicAccount {

    constructor(
        fileName: String,
        networkType: NetworkType,
        nodeProvider: NodeProvider
    ) : this(
        fileName = fileName,
        filePath = "",
        usoamic = Usoamic(
            networkType = networkType,
            nodeProvider = nodeProvider
        )
    )

    constructor(
        fileName: String,
        filePath: String,
        networkType: NetworkType,
        nodeProvider: NodeProvider
    ) : this(
        fileName = fileName,
        filePath = filePath,
        usoamic = Usoamic(
            networkType = networkType,
            nodeProvider = nodeProvider
        )
    )

    constructor(
        fileName: String,
        filePath: String,
        contractAddress: String,
        node: String
    ) : this(
        fileName = fileName,
        filePath = filePath,
        usoamic = Usoamic(
            contractAddress = contractAddress,
            node = node
        )
    )

    override fun getUsoBalance(): BigInteger? {
        return usoamic.balanceOf(address)
    }

    override fun balanceOf(address: String): BigInteger? {
        return usoamic.balanceOf(address)
    }

    override fun burn(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.burn(
            credentials = getCredentials(password),
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun transferUso(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.transferUso(
            credentials = getCredentials(password),
            to = to,
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun getSupply(): BigInteger? {
        return usoamic.getSupply(
            addressOfRequester = address
        )
    }

    override fun getVersion(): String? {
        return usoamic.getVersion(
            addressOfRequester = address
        )
    }

    override fun getEthBalance(): BigInteger {
        return usoamic.getEthBalance(address)
    }

    override fun getEthBalance(address: String): BigInteger {
        return usoamic.getEthBalance(address)
    }

    override fun getConvertedBalance(unit: Convert.Unit): BigDecimal {
        return Convert.fromWei(getEthBalance().toString(), unit)
    }

    override fun getEthLastBlock(): EthBlock.Block {
        return usoamic.getEthLastBlock()
    }

    override fun getEthHeight(): BigInteger {
        return usoamic.getEthHeight()
    }
}
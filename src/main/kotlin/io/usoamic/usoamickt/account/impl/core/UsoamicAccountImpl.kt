package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.UsoamicAccount
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.enumcls.TxSpeed
import java.math.BigInteger

class UsoamicAccountImpl constructor(
    private val usoamic: Usoamic
) : SwapImpl(usoamic), UsoamicAccount {
    constructor(
        fileName: String,
        networkType: NetworkType,
        nodeProvider: NodeProvider
    ) : this(
        usoamic = Usoamic(
            fileName = fileName,
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
        usoamic = Usoamic(
            fileName = fileName,
            filePath = filePath,
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
        usoamic = Usoamic(
            fileName = fileName,
            filePath = filePath,
            contractAddress = contractAddress,
            node = node
        )
    )

    override fun getUsoBalance(): BigInteger? {
        return usoamic.getUsoBalance()
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
            password = password,
            value = value,
            txSpeed = txSpeed)
    }

    override fun transferUso(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed
    ): String {
        return usoamic.transferUso(
            password = password,
            to = to,
            value = value,
            txSpeed = txSpeed
        )
    }

    override fun getSupply(): BigInteger? {
        return usoamic.getSupply()
    }

    override fun getVersion(): String? {
        return usoamic.getVersion()
    }
}
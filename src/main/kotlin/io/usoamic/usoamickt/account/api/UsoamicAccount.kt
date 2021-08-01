package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed
import java.math.BigInteger

interface UsoamicAccount : Swap,
    TransactionExplorer,
    Purchases,
    Notes,
    Ideas,
    Owner,
    TransactionManager,
    AccountWrapper,
    EthereumCore,
    AccountManager {

    fun getUsoBalance(): BigInteger?

    fun balanceOf(address: String): BigInteger?

    fun burn(
        password: String,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun transferUso(
        password: String,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun getSupply(): BigInteger?

    fun getVersion(): String?
}
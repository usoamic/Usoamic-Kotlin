package io.usoamic.usoamickt.account.api

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger


interface AccountWrapper {
    val hasAccount: Boolean

    val address: String

    fun getAddress(password: String): String

    fun removeWallet(): Boolean

    fun getEthBalance(): BigInteger

    fun getConvertedBalance(unit: Convert.Unit = Convert.Unit.ETHER): BigDecimal
}
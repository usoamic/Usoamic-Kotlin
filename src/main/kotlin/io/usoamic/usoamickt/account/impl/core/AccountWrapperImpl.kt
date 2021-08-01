package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.AccountWrapper
import io.usoamic.usoamickt.core.Usoamic
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

open class AccountWrapperImpl(
    private val usoamic: Usoamic
) :
    EthereumCoreImpl(usoamic), AccountWrapper {
    override val hasAccount: Boolean
        get() = usoamic.hasAccount
    override val address: String
        get() = usoamic.address

    override fun getAddress(password: String): String {
        return usoamic.getAddress(password)
    }

    override fun removeWallet(): Boolean {
        return usoamic.removeWallet()
    }

    override fun getEthBalance(): BigInteger {
        return usoamic.getEthBalance()
    }

    override fun getConvertedBalance(unit: Convert.Unit): BigDecimal {
        return usoamic.getConvertedBalance(unit)
    }

}
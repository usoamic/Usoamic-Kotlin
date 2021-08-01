package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.Owner
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed

open class OwnerImpl constructor(
    private val usoamic: Usoamic
) : TransactionManagerImpl(usoamic),
    Owner {
    override fun setFrozen(
        password: String,
        frozen: Boolean,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setFrozen(
            password = password,
            frozen = frozen,
            txSpeed = txSpeed
        )
    }

    override fun setOwner(
        password: String,
        newOwner: String,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setOwner(
            password = password,
            newOwner = newOwner,
            txSpeed = txSpeed
        )
    }

}
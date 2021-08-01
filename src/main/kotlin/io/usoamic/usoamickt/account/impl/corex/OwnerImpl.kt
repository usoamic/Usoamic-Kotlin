package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.Owner
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.enumcls.TxSpeed

open class OwnerImpl constructor(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : TransactionManagerImpl(
    fileName = fileName,
    filePath = filePath,
    usoamic = usoamic
), Owner {
    override fun setFrozen(
        password: String,
        frozen: Boolean,
        txSpeed: TxSpeed
    ): String {
        return usoamic.setFrozen(
            credentials = getCredentials(password),
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
            credentials = getCredentials(password),
            newOwner = newOwner,
            txSpeed = txSpeed
        )
    }

}
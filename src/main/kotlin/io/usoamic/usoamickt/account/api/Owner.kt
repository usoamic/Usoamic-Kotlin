package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.enumcls.TxSpeed

interface Owner {
    fun setFrozen(
        password: String,
        frozen: Boolean,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String

    fun setOwner(
        password: String,
        newOwner: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String
}
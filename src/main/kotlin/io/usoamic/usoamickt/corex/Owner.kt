package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.crypto.Credentials

open class Owner(
    contractAddress: String,
    node: String
) : TransactionManager(
    contractAddress = contractAddress,
    node = node
) {
    fun setFrozen(
        credentials: Credentials,
        frozen: Boolean,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "setFronzen",
        inputParameters = listOf(Bool(frozen)),
        txSpeed = txSpeed
    )

    fun setOwner(
        credentials: Credentials,
        newOwner: String,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "setOwner",
        inputParameters = listOf(
            Address(newOwner)
        ),
        txSpeed = txSpeed
    )
}
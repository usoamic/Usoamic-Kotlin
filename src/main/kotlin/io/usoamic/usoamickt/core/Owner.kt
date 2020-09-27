package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool

open class Owner constructor(fileName: String, filePath: String, contractAddress: String, node: String) : TransactionManager(fileName, filePath, contractAddress, node) {
    fun setFrozen(password: String, frozen: Boolean, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(password, "setFronzen", listOf(Bool(frozen)), txSpeed)

    fun setOwner(password: String, newOwner: String, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(password, "setOwner", listOf(Address(newOwner)), txSpeed)
}
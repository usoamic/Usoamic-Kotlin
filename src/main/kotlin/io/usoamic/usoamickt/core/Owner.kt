package io.usoamic.usoamickt.core

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool

open class Owner constructor(fileName: String, filePath: String, contractAddress: String, node: String) : TransactionManager(fileName, filePath, contractAddress, node) {
    fun setFrozen(password: String, frozen: Boolean): String = executeTransaction(password, "setFronzen", listOf(Bool(frozen)))

    fun setOwner(password: String, newOwner: String): String = executeTransaction(password, "setOwner", listOf(Address(newOwner)))
}
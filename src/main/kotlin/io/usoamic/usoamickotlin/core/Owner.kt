package io.usoamic.usoamickotlin.core

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool

open class Owner constructor(filename: String, contractAddress: String, node: String) : TransactionManager(filename, contractAddress, node) {
    @Throws(Exception::class)
    fun setFrozen(password: String, frozen: Boolean): String = executeTransaction(password, "setFronzen", listOf(Bool(frozen)))

    @Throws(Exception::class)
    fun setOwner(password: String, newOwner: String): String = executeTransaction(password, "setOwner", listOf(Address(newOwner)))
}
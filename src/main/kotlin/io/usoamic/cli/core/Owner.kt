package io.usoamic.cli.core

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function

open class Owner constructor(filename: String) : TransactionManager(filename) {
    @Throws(Exception::class)
    fun setFrozen(password: String, frozen: Boolean): String {
        val function = Function(
            "setFronzen",//TODO: Fix typo after release new contract version
            listOf(Bool(frozen)),
            emptyList()
        )
        return executeTransaction(password, function)
    }

    @Throws(Exception::class)
    fun setOwner(password: String, newOwner: String): String {
        val function = Function(
            "setOwner",
            listOf(Address(newOwner)),
            emptyList()
        )
        return executeTransaction(password, function)
    }
}
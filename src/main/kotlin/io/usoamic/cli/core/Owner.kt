package io.usoamic.cli.core

import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function

open class Owner constructor(filename: String) : TransactionManager(filename) {
    @Throws(Exception::class)
    fun setFronzen(password: String, frozen: Boolean): String {
        val function = Function(
            "setFronzen",
            listOf(Bool(frozen)),
            emptyList()
        )
        return executeTransaction(password, function)
    }
}
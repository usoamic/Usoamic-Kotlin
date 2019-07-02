package io.usoamic.cli

import io.usoamic.cli.contract.Contract
import java.io.FileWriter
import java.math.BigInteger
import javax.inject.Inject
import com.google.gson.GsonBuilder


class Usoamic {
    @Inject
    lateinit var contract: Contract

    init {
        App.component.inject(this)
        FileWriter(Config.ACCOUNT_FILENAME).use { writer ->
            val gson = GsonBuilder().create()
            gson.toJson(Account("address", "path", "name", BigInteger.TWO), writer)
        }
        val addr = contract.getAddress()
        println(addr)
    }
}
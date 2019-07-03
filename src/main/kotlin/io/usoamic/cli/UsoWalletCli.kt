package io.usoamic.cli

import io.usoamic.cli.contract.Usoamic
import javax.inject.Inject


class UsoWalletCli {
    @Inject
    lateinit var contract: Usoamic

    init {
        App.component.inject(this)

//        val addr = contract.getAddress()
//        println(addr)
    }
}
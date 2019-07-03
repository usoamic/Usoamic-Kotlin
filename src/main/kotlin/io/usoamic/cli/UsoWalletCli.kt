package io.usoamic.cli

import io.usoamic.cli.contract.Usoamic
import javax.inject.Inject


class UsoWalletCli {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        App.component.inject(this)

//        val addr = usoamic.getAddress()
//        println(addr)
    }
}
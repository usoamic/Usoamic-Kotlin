package io.usoamic.cli

import io.usoamic.cli.di.AppComponent
import io.usoamic.cli.di.UsoamicModule
import io.usoamic.cli.di.DaggerAppComponent

object App {
    lateinit var component: AppComponent

    private fun prepareDagger() {
        component = DaggerAppComponent
            .builder()
            .contractModule(UsoamicModule())
            .build()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        prepareDagger()
        UsoWalletCli()
    }
}
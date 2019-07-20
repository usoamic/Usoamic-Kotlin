package io.usoamic.cli

import io.usoamic.cli.di.AppComponent
import io.usoamic.cli.di.DaggerAppComponent
import io.usoamic.cli.di.UsoamicModule

object App {
    lateinit var component: AppComponent

    private fun prepareDagger() {
        component = DaggerAppComponent
            .builder()
            .usoamicModule(UsoamicModule())
            .build()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        prepareDagger()
        UsoWalletCli()
    }
}
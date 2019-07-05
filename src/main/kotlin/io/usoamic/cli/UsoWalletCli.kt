package io.usoamic.cli

import io.usoamic.cli.core.Usoamic
import javax.inject.Inject


class UsoWalletCli {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        App.component.inject(this)
    }
}
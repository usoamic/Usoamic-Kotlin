package io.usoamic.cli.di

import io.usoamic.cli.core.AccountWrapper
import io.usoamic.cli.core.TransactionManager
import dagger.Component
import io.usoamic.cli.UsoWalletCli
import io.usoamic.cli.core.Usoamic
import javax.inject.Singleton

@Singleton
@Component(modules = [UsoamicModule::class, Web3jModule::class])
interface AppComponent {
    fun inject(clazz: Usoamic)
    fun inject(clazz: AccountWrapper)
    fun inject(clazz: TransactionManager)
    fun inject(clazz: UsoWalletCli)
}
package io.usoamic.cli.di

import io.usoamic.cli.contract.AccountWrapper
import io.usoamic.cli.contract.TransactionManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContractModule::class])
interface AppComponent {
    fun inject(clazz: io.usoamic.cli.contract.Usoamic)
    fun inject(clazz: AccountWrapper)
    fun inject(clazz: TransactionManager)
    fun inject(clazz: Usoamic)
}
package io.usoamic.cli.di

import io.usoamic.cli.contract.AccountManager
import io.usoamic.cli.contract.Contract
import io.usoamic.cli.contract.TransactionManager
import dagger.Component
import io.usoamic.cli.Usoamic
import javax.inject.Singleton

@Singleton
@Component(modules = [ContractModule::class])
interface AppComponent {
    fun inject(clazz: Contract)
    fun inject(clazz: AccountManager)
    fun inject(clazz: TransactionManager)
    fun inject(clazz: Usoamic)
}
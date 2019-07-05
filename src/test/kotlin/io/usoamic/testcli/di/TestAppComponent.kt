package io.usoamic.testcli.di

import dagger.Component
import io.usoamic.testcli.*

import javax.inject.Singleton

@Singleton
@Component(modules = [TestWeb3jModule::class, TestUsoamicModule::class])
interface TestAppComponent {
    fun inject(clazz: NotesTest)
    fun inject(clazz: OwnerTest)
    fun inject(clazz: PurchasesTest)
    fun inject(clazz: SwapTest)
    fun inject(clazz: TransactionExplorerTest)
    fun inject(clazz: UsoamicTest)
    fun inject(clazz: IdeasTest)
}
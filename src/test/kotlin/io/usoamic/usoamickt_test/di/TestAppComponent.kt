package io.usoamic.usoamickt_test.di

import dagger.Component
import io.usoamic.usoamickotlin_test.*
import io.usoamic.usoamickt_test.*

import javax.inject.Singleton

@Singleton
@Component(modules = [TestUsoamicModule::class])
interface TestAppComponent {
    fun inject(clazz: NotesTest)
    fun inject(clazz: OwnerTest)
    fun inject(clazz: PurchasesTest)
    fun inject(clazz: SwapTest)
    fun inject(clazz: TransactionExplorerTest)
    fun inject(clazz: UsoamicTest)
    fun inject(clazz: IdeasTest)
    fun inject(clazz: AccountManagerTest)
}
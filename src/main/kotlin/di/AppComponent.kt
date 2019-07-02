package di

import contract.AccountManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AccountModule::class])
interface AppComponent {
    fun inject(clazz: AccountManager)
}
import contract.AccountManager
import di.AccountModule
import di.AppComponent
import di.DaggerAppComponent

object Usoamic {
    lateinit var component: AppComponent

    private fun prepareDagger() {
        component = DaggerAppComponent
            .builder()
            .accountModule(AccountModule())
            .build()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        prepareDagger()
    }
}
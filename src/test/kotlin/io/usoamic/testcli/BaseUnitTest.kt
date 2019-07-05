package io.usoamic.testcli

import io.usoamic.cli.App
import io.usoamic.cli.di.DaggerAppComponent
import io.usoamic.testcli.di.UsoamicModule
import io.usoamic.testcli.di.DaggerTestAppComponent
import io.usoamic.testcli.di.TestAppComponent
import io.usoamic.testcli.di.Web3jModule
import org.junit.Before

class BaseUnitTest {
    companion object {
        lateinit var componentTest: TestAppComponent
    }

    @Before
    fun prepareDagger() {
        componentTest = DaggerTestAppComponent
            .builder()
            .usoamicModule(UsoamicModule())
            .web3jModule(Web3jModule())
            .build()
    }
}
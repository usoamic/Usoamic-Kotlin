package io.usoamic.testcli

import io.usoamic.testcli.di.DaggerTestAppComponent
import io.usoamic.testcli.di.TestAppComponent
import io.usoamic.testcli.di.TestUsoamicModule
import io.usoamic.testcli.di.TestWeb3jModule
import org.junit.jupiter.api.BeforeAll

class BaseUnitTest {
    companion object {
        var componentTest: TestAppComponent = buildDagger()
        private fun buildDagger(): TestAppComponent {
            return DaggerTestAppComponent
                .builder()
                .testUsoamicModule(TestUsoamicModule())
                .testWeb3jModule(TestWeb3jModule())
                .build()
        }

        @BeforeAll
        fun prepareDagger() {
            componentTest = buildDagger()
        }
    }
}
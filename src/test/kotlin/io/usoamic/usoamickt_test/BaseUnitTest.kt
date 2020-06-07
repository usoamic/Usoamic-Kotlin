package io.usoamic.usoamickt_test

import io.usoamic.usoamickt_test.di.DaggerTestAppComponent
import io.usoamic.usoamickt_test.di.TestAppComponent
import io.usoamic.usoamickt_test.di.TestUsoamicModule

class BaseUnitTest {
    companion object {
        val componentTest: TestAppComponent by lazy { buildDagger() }

        private fun buildDagger(): TestAppComponent {
            return DaggerTestAppComponent
                .builder()
                .testUsoamicModule(TestUsoamicModule())
                .build()
        }
    }
}
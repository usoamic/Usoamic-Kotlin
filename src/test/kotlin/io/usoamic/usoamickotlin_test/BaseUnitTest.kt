package io.usoamic.usoamickotlin_test

import io.usoamic.usoamickotlin_test.di.DaggerTestAppComponent
import io.usoamic.usoamickotlin_test.di.TestAppComponent
import io.usoamic.usoamickotlin_test.di.TestUsoamicModule

class BaseUnitTest {
    companion object {
        val componentTest: TestAppComponent = buildDagger()
        private fun buildDagger(): TestAppComponent {
            return DaggerTestAppComponent
                .builder()
                .testUsoamicModule(TestUsoamicModule())
                .build()
        }

    }
}
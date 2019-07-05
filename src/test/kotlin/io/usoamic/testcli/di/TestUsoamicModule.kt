package io.usoamic.testcli.di

import io.usoamic.cli.core.Usoamic
import dagger.Module
import dagger.Provides
import io.usoamic.cli.other.Config
import io.usoamic.testcli.other.TestConfig
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(TestConfig.ACCOUNT_FILENAME, TestConfig.NODE)
    }
}
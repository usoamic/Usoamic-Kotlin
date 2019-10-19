package io.usoamic.usoamickt_test.di

import dagger.Module
import dagger.Provides
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt_test.other.TestConfig
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(TestConfig.ACCOUNT_FILENAME, TestConfig.CONTRACT_ADDRESS, TestConfig.NODE)
    }
}
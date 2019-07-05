package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.cli.other.Config
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import javax.inject.Singleton

@Module
class TestWeb3jModule {
    @Provides
    @Singleton
    fun provideWeb3j(): Web3j {
        return Web3j.build(HttpService(Config.NODE))
    }
}
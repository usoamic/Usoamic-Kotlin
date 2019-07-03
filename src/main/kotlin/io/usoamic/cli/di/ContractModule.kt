package io.usoamic.cli.di

import io.usoamic.cli.contract.Contract
import dagger.Module
import dagger.Provides
import io.usoamic.cli.other.Config

@Module
class ContractModule {
    @Provides
    fun provideContract(): Contract {
        return Contract(Config.ACCOUNT_FILENAME)
    }
}
package io.usoamic.cli.di

import io.usoamic.cli.contract.Contract
import dagger.Module
import dagger.Provides
import io.usoamic.cli.Account
import io.usoamic.cli.Config
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Singleton

@Module
class ContractModule {
    @Provides
    fun provideContract(): Contract {
        return Contract(Config.ACCOUNT_FILENAME)
    }
}
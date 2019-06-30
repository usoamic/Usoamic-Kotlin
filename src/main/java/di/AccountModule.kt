package di

import dagger.Module
import dagger.Provides
import model.Account
import other.Config
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Singleton

@Module
class AccountModule {
    @Provides
    @Singleton
    fun provideAccount(): Account {
        val json = Files.readString(Path.of(Config.ACCOUNT_FILENAME))
        return Account.fromJson(json)
    }
}
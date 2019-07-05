package io.usoamic.testcli

import io.usoamic.cli.core.AccountManager
import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.exception.InvalidMnemonicPhraseException
import io.usoamic.cli.other.Config
import io.usoamic.testcli.other.TestConfig
import org.junit.Test
import org.web3j.protocol.Web3j
import javax.inject.Inject

class UsoamicTest {
    @Inject
    lateinit var usoamic: Usoamic

    @Inject
    lateinit var web3j: Web3j

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test(expected = InvalidMnemonicPhraseException::class)
    fun accountTestWhenMnemonicPhraseIsInvalid() {
        usoamic.importMnemonic(TestConfig.PASSWORD, "culture into")
    }

    @Test(expected = InvalidMnemonicPhraseException::class)
    fun accountTestWhenMnemonicPhraseIsEmpty() {
        usoamic.importMnemonic(TestConfig.PASSWORD, "")
    }

    @Test
    fun accountTest() {
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(TestConfig.PASSWORD, mnemonicPhrase)
        assert(usoamic.account.name == fileName)
    }

    @Test
    fun transferTest() {
        assert(true)
    }
}
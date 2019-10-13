package io.usoamic.usoamickotlin_test

import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin_test.other.TestConfig
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import java.io.File
import javax.inject.Inject

class AccountManagerTest {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun accountTest() {
        val file = File(TestConfig.ACCOUNT_FILENAME)
        if(file.exists()) {
            file.delete()
        }
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(TestConfig.PASSWORD, mnemonicPhrase)
        assert(WalletUtils.isValidAddress(usoamic.address))
        assert(usoamic.address == TestConfig.DEFAULT_ADDRESS)
    }

    @Test
    @RepeatedTest(500)
    fun testAddresses() {
        val credentials = Credentials.create(Keys.createEcKeyPair())
        assert(WalletUtils.isValidAddress(credentials.address)/* && WalletUtils.isValidPrivateKey(credentials.ecKeyPair.privateKey.toString(16))*/)
    }

    @Test
    fun accountTestWhenMnemonicPhraseIsEmpty() {
        assertThrows<InvalidMnemonicPhraseException> {
            usoamic.importMnemonic(TestConfig.PASSWORD, "")
        }
    }

    @Test
    fun accountTestWhenMnemonicPhraseIsInvalid() {
        assertThrows<InvalidMnemonicPhraseException> {
            usoamic.importMnemonic(TestConfig.PASSWORD, "culture into")
        }
    }
}
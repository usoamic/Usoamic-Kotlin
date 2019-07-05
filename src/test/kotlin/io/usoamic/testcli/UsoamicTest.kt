package io.usoamic.testcli

import io.usoamic.cli.core.AccountManager
import io.usoamic.cli.core.Usoamic
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

    @Test
    fun accountTest() {
        val password = "1234!"
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(password, mnemonicPhrase)
        assert(usoamic.account.name == fileName)
    }

    @Test
    fun transferTest() {
        assert(true)
    }
}
package io.usoamic.testcli

import io.usoamic.cli.core.AccountManager
import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.other.Config
import org.junit.Test
import org.web3j.protocol.Web3j
import javax.inject.Inject

class UsoamicTest {
    @Inject
    lateinit var usoamic: Usoamic

    @Inject
    lateinit var web3j: Web3j

    init {
//        App.componentTest.inject(this)
    }

    @Test
    fun accountTest() {
        val password = "1234!"
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(password, mnemonicPhrase)
        assert(fileName == Config.ACCOUNT_FILENAME)

    }

    @Test
    fun transferTest() {

    }
}
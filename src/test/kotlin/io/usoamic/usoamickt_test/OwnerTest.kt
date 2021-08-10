package io.usoamic.usoamickt_test

import io.usoamic.usoamickt.account.api.UsoamicAccount
import io.usoamic.usoamickt_test.other.TestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.exceptions.MessageDecodingException
import javax.inject.Inject

class OwnerTest {
    @Inject
    lateinit var usoamic: UsoamicAccount

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun setFrozenTest() {
        assertThrows<MessageDecodingException> {
            usoamic.setFrozen(TestConfig.PASSWORD, true)
        }
    }

    @Test
    fun setOwnerTest() {
        assertThrows<MessageDecodingException> {
            val credentials = Credentials.create(Keys.createEcKeyPair())
            usoamic.setOwner(TestConfig.PASSWORD, credentials.address)
        }
    }
}
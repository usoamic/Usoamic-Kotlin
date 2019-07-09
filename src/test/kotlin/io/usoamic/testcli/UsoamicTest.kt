package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.exception.InvalidMnemonicPhraseException
import io.usoamic.cli.util.Coin
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.*

import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
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
        val path = Path.of(TestConfig.ACCOUNT_FILENAME)
        if(Files.exists(path)) {
            Files.delete(path)
        }
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(TestConfig.PASSWORD, mnemonicPhrase)
        println("File name: $fileName")
        println("Usoamic File name: ${usoamic.account.name}")
        assert(WalletUtils.isValidAddress(usoamic.account.address))
        assert(usoamic.account.address == TestConfig.DEFAULT_ADDRESS)
    }

    @Test
    @RepeatedTest(5)
    fun testAddresses() {
        val credentials = Credentials.create(Keys.createEcKeyPair())
        assert(WalletUtils.isValidAddress(credentials.address) && WalletUtils.isValidPrivateKey(credentials.ecKeyPair.privateKey.toString(16)))
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

    @Test
    fun burnTest() {
        val address = usoamic.account.address
        val balance = usoamic.balanceOf(address)!!
        val value = Coin.fromCoin("1.2345").toSat()

        assert(balance >= value) {
            println("Need more tokens: $value")
        }
        val ethBalance = usoamic.getBalance()

        assert(ethBalance > BigInteger.ZERO) {
            println("Need more Ether: $ethBalance")
        }

        val estimatedBalance = balance.subtract(value)!!

        val txHash = usoamic.burn(TestConfig.PASSWORD, value)
        usoamic.waitTransactionReceipt(txHash) {
            val newBalance = usoamic.balanceOf(address)

            println("New Balance: $newBalance")

            assert(estimatedBalance.compareTo(newBalance) == 0)
        }
    }

    @Test
    fun transferTest() {
        val bobCredentials = Credentials.create(Keys.createEcKeyPair())
        println("BobPrivateKey: ${bobCredentials.ecKeyPair.privateKey}")

        val alice = usoamic.account.address
        val bob = bobCredentials.address
        val aliceBalance = usoamic.balanceOf(alice)!!
        val bobBalance = usoamic.balanceOf(bob)!!

        println("Alice balance: $aliceBalance")
        println("Bob balance: $bobBalance")

        val value = Coin.fromCoin("1.2345").toSat()

        assert(aliceBalance >= value) {
            println("Need more tokens: $value")
        }

        val aliceEthBalance = usoamic.getBalance()
        assert(aliceEthBalance > BigInteger.ZERO) {
            println("Need more Ether: $aliceEthBalance")
        }

        val estimatedAliceBalance = aliceBalance.subtract(value)
        val estimatedBobBalance = bobBalance.add(value)

        val txHash = usoamic.transfer(TestConfig.PASSWORD, bob, value)
        usoamic.waitTransactionReceipt(txHash) {
            val newAliceBalance = usoamic.balanceOf(alice)
            val newBobBalance = usoamic.balanceOf(bob)

            println("New Alice Balance: $newAliceBalance")
            println("New Bob Balance: $newBobBalance")

            assert(estimatedAliceBalance?.compareTo(newAliceBalance) == 0)
            assert(estimatedBobBalance?.compareTo(newBobBalance) == 0)
        }
    }

    @Test
    fun supplyTest() {
        val supply = usoamic.getSupply()
        assert(supply!! >= BigInteger.ZERO)
    }

    @Test
    fun versionTest() {
        val version = usoamic.getVersion()
        require(version == TestConfig.VERSION)
    }
}
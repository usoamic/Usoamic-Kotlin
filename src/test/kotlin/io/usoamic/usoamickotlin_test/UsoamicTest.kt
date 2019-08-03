package io.usoamic.usoamickotlin_test

import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin.util.Coin
import io.usoamic.usoamickotlin_test.other.TestConfig
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject

class UsoamicTest {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun balanceOfTest() {
        val balance = usoamic.balanceOf(TestConfig.OWNER_ADDRESS)!!
        require(balance >= BigInteger.ZERO)
    }

    @Test
    fun burnTest() {
        val address = usoamic.address
        val balance = usoamic.balanceOf(address)!!
        val value = Coin.fromCoin("1.2345").toSat()

        assert(balance >= value) {
            println("Need more tokens: $value")
        }
        val ethBalance = usoamic.getEthBalance()

        assert(ethBalance > BigInteger.ZERO) {
            println("Need more Ether: $ethBalance")
        }

        val estimatedBalance = balance.subtract(value)!!

        val txHash = usoamic.burn(TestConfig.PASSWORD, value)
        usoamic.waitTransactionReceipt(txHash) {
            val newBalance = usoamic.balanceOf(address)
            assert(estimatedBalance.compareTo(newBalance) == 0)
        }
    }

    @Test
    fun transferTest() {
        val bobCredentials = Credentials.create(Keys.createEcKeyPair())
        println("BobPrivateKey: ${bobCredentials.ecKeyPair.privateKey}")

        val alice = usoamic.address
        val bob = bobCredentials.address
        val aliceBalance = usoamic.balanceOf(alice)!!
        val bobBalance = usoamic.balanceOf(bob)!!

        println("Alice balance: $aliceBalance")
        println("Bob balance: $bobBalance")

        val value = Coin.fromCoin("1.2345").toSat()

        assert(aliceBalance >= value) {
            println("Need more tokens: $value")
        }

        val aliceEthBalance = usoamic.getEthBalance()
        assert(aliceEthBalance > BigInteger.ZERO) {
            println("Need more Ether: $aliceEthBalance")
        }

        val estimatedAliceBalance = aliceBalance.subtract(value)
        val estimatedBobBalance = bobBalance.add(value)

        val txHash = usoamic.transferUso(TestConfig.PASSWORD, bob, value)
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
    fun getSupplyTest() {
        val supply = usoamic.getSupply()
        assert(supply!! >= BigInteger.ZERO)
    }

    @Test
    fun getVersionTest() {
        val version = usoamic.getVersion()
        require(version == TestConfig.VERSION)
    }
}
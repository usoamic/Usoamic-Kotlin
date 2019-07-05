package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.cli.exception.InvalidMnemonicPhraseException
import io.usoamic.cli.util.Coin
import io.usoamic.testcli.other.TestConfig
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UsoamicTest {
    @Inject
    lateinit var usoamic: Usoamic

    @Inject
    lateinit var web3j: Web3j

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test(expected = InvalidMnemonicPhraseException::class)
    fun a_accountTestWhenMnemonicPhraseIsEmpty() {
        usoamic.importMnemonic(TestConfig.PASSWORD, "")
    }

    @Test(expected = InvalidMnemonicPhraseException::class)
    fun b_accountTestWhenMnemonicPhraseIsInvalid() {
        usoamic.importMnemonic(TestConfig.PASSWORD, "culture into")
    }

    @Test
    fun c_accountTest() {
//        Files.delete(Path.of(TestConfig.ACCOUNT_FILENAME))
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        val fileName = usoamic.importMnemonic(TestConfig.PASSWORD, mnemonicPhrase)
        assert(usoamic.account.name == fileName)
        assert(usoamic.account.address == "0x8b27fa2987630a1acd8d868ba84b2928de737bc2")
    }

    @Test
    fun d_transferTest() {
        val bobCredentials = Credentials.create(Keys.createEcKeyPair())
        println("BobPrivateKey: ${bobCredentials.ecKeyPair.privateKey}")

        val alice = usoamic.account.address
        val bob = bobCredentials.address
        val aliceBalance = usoamic.balanceOf(alice)
        val bobBalance = usoamic.balanceOf(bob)

        println("Alice balance: $aliceBalance")
        println("Bob balance: $bobBalance")

        val value = Coin.fromCoin("1.2345").toSat()

        assert(aliceBalance!! >= value) {
            println("Need more tokens: $value")
        }

        val aliceEthBalance = usoamic.getBalance()
        assert(aliceEthBalance > BigInteger.ZERO) {
            println("Need more Ether: $aliceEthBalance")
        }

        val needAliceBalance = aliceBalance.subtract(value)
        val needBobBalance = bobBalance?.add(value)

        println("Need Alice Balance: $needAliceBalance")
        println("Need Bob Balance: $needBobBalance")

        val txHash = usoamic.transfer(TestConfig.PASSWORD, bob, value)
        while (true) {
            val transactionReceipt = web3j.ethGetTransactionReceipt(txHash).send()
            if (transactionReceipt.result != null) {
                break
            }
            println("Waiting confirmation")
            Thread.sleep(15000)
        }

        val newAliceBalance = usoamic.balanceOf(alice)
        val newBobBalance = usoamic.balanceOf(bob)

        println("New Alice Balance: $newAliceBalance")
        println("New Bob Balance: $newBobBalance")

        assert(needAliceBalance?.compareTo(newAliceBalance) == 0)
        assert(needBobBalance?.compareTo(newBobBalance) == 0)
    }
}
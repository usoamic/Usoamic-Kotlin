package io.usoamic.usoamickt_test

import io.usoamic.usoamickt.account.api.UsoamicAccount
import io.usoamic.usoamickt.util.Coin
import io.usoamic.usoamickt_test.other.TestConfig
import org.junit.jupiter.api.Test
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import java.math.BigInteger
import javax.inject.Inject

class TransactionExplorerTest {
    @Inject
    lateinit var usoamic: UsoamicAccount

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun getNumberOfTransactionsByAddressTest() {
        val credentials = Credentials.create(Keys.createEcKeyPair())
        val numberOfTx = usoamic.getNumberOfTransactionsByAddress(credentials.address)
        assert(numberOfTx == BigInteger.ZERO)

        val defaultNumberOfTx = usoamic.getNumberOfTransactionsByAddress(TestConfig.DEFAULT_ADDRESS)!!
        assert(defaultNumberOfTx >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfTransactionsTest() {
        val numberOfTx = usoamic.getNumberOfTransactions()!!
        assert(numberOfTx >= BigInteger.ZERO)
    }

    @Test
    fun getTransactionTest() {
        val credentials = Credentials.create(Keys.createEcKeyPair())
        val coin = Coin.fromCoin("10.231")

        val txHash = usoamic.transferUso(TestConfig.PASSWORD, credentials.address, coin.toSat())

        usoamic.waitTransactionReceipt(txHash) {
            val numberOfTx = usoamic.getNumberOfTransactions()!!.subtract(BigInteger.ONE)
            val transaction = usoamic.getTransaction(numberOfTx)
            assert(transaction.isExist)
            assert(transaction.from == TestConfig.DEFAULT_ADDRESS)
            assert(transaction.to == credentials.address)
            assert(transaction.txId == numberOfTx)
            assert(transaction.value == coin.toBigDecimal())
        }
    }
}
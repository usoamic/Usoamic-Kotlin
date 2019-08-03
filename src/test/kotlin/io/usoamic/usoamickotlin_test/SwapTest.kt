package io.usoamic.usoamickotlin_test

import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import io.usoamic.usoamickotlin_test.other.TestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.exceptions.MessageDecodingException
import org.web3j.utils.Convert
import java.math.BigInteger
import javax.inject.Inject

class SwapTest {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun depositEthTest() {
        val value = Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger()
        val txHash = usoamic.transferEth(TestConfig.PASSWORD, TestConfig.CONTRACT_ADDRESS, value)
        usoamic.waitTransactionReceipt(txHash) {
            assert(it.status == "0x0")
        }
    }

    @Test
    fun withdrawEthTest() {
        assertThrows<MessageDecodingException> {
            usoamic.withdrawEth(TestConfig.PASSWORD, Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger())
        }
    }

    @Test
    fun getSwappableTest() {
        assert(usoamic.getSwappable() != null)
    }

    @Test
    fun getSwapBalanceTest() {
        val swapBalance = usoamic.getSwapBalance()!!
        require(swapBalance >= BigInteger.ZERO)
    }

    @Test
    fun setSwapRateTest() {
        assertThrows<MessageDecodingException> {
            usoamic.setSwapRate(TestConfig.PASSWORD, BigInteger.ONE)
        }
    }

    @Test
    fun setSwappableTest() {
        assertThrows<MessageDecodingException> {
            val swappable = usoamic.getSwappable()!!
            usoamic.setSwappable(TestConfig.PASSWORD, !(swappable))
        }
    }

    @Test
    fun getSwapRateTest() {
        val swapRate = usoamic.getSwapRate()!!
        assert(swapRate >= BigInteger.ONE)
    }

    @Test
    fun burnSwapTest() {
        assert(usoamic.getSwappable()!!) {
            println("Swap disabled!")
        }

        val value = Coin.ONE_HUNDRED.toSat()
        val accountBalance = usoamic.getEthBalance()
        val accountTokenBalance = usoamic.getUsoBalance()!!

        assert(accountTokenBalance > value)

        val contractBalance = usoamic.getEthBalance(TestConfig.CONTRACT_ADDRESS)

        val swapRate = usoamic.getSwapRate()!!
        val numberOfWei = swapRate.multiply(value)!!

        assert(contractBalance > numberOfWei) {
            println("Need more ethers on contract address ${TestConfig.CONTRACT_ADDRESS}")
        }

        val txHash = usoamic.burnSwap(TestConfig.PASSWORD, value)
        usoamic.waitTransactionReceipt(txHash) {
            val newAccountBalance = usoamic.getEthBalance()
            val newAccountTokenBalance = usoamic.getUsoBalance()
            val newContractBalance = usoamic.getEthBalance(TestConfig.CONTRACT_ADDRESS)

            assert(accountTokenBalance!!.subtract(newAccountTokenBalance).compareTo(value) == 0)

            val ethValue = value.multiply(swapRate)

            val maxFee = BigInteger("-10000000000000000")

            assert(newAccountBalance.subtract(accountBalance).subtract(ethValue) >= maxFee)
            assert(contractBalance.subtract(newContractBalance).compareTo(ethValue) == 0)
        }
    }

}
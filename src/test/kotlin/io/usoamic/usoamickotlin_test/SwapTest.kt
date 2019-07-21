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
        val txHash = usoamic.transferEther(TestConfig.PASSWORD, TestConfig.CONTRACT_ADDRESS, value)
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
        usoamic.getSwappable()
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
        val value = Coin.ONE_HUNDRED.toSat()

        val address = usoamic.address

        val accountBalance = usoamic.getEthBalance(address)
        val accountTokenBalance = usoamic.balanceOf(address)
        val contractBalance = usoamic.getEthBalance(TestConfig.CONTRACT_ADDRESS)

        val txHash = usoamic.burnSwap(TestConfig.PASSWORD, value)
        usoamic.waitTransactionReceipt(txHash) {
            val newAccountBalance = usoamic.getEthBalance(address)
            val newAccountTokenBalance = usoamic.balanceOf(address)
            val newContractBalance = usoamic.getEthBalance(TestConfig.CONTRACT_ADDRESS)

            assert(accountTokenBalance!!.subtract(newAccountTokenBalance).compareTo(value) == 0)

            val swapRate = usoamic.getSwapRate()
            val ethValue = value.multiply(swapRate)

            val maxFee = BigInteger("-10000000000000000")

            assert(newAccountBalance.subtract(accountBalance).subtract(ethValue) >= maxFee)
            assert(contractBalance.subtract(newContractBalance).compareTo(ethValue) == 0)
        }
    }

}
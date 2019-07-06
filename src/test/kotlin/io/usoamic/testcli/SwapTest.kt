package io.usoamic.testcli

import io.usoamic.cli.core.Usoamic
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
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
}
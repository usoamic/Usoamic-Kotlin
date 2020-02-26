package io.usoamic.usoamickt_test.other

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.other.Contract

class TestConfig {
    companion object {
        const val ACCOUNT_FILENAME: String = "test_account.json"
        val CONTRACT_ADDRESS: String = Contract.forNetwork(NetworkType.TESTNET)
        const val DEFAULT_ADDRESS: String = "0x8b27fa2987630a1acd8d868ba84b2928de737bc2"
        const val CONTRACT_CREATOR_ADDRESS: String = "0xcabE4d50Fe44adc481FE965ecfc1B0B9a4389640"
        const val PASSWORD: String = "1234!"
        const val VERSION: String = "v2.1.1"
    }
}
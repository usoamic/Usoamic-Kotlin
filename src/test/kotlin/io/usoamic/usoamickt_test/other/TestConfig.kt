package io.usoamic.usoamickt_test.other

import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import io.usoamic.usoamickt.other.Contract
import io.usoamic.usoamickt.other.Node

class TestConfig {
    companion object {
        val NODE: String = Node.by(NetworkType.TESTNET, NodeProvider.INFURA)
        const val ACCOUNT_FILENAME: String = "test_account.json"
        val CONTRACT_ADDRESS: String = Contract.forNetwork(NetworkType.TESTNET)
        const val DEFAULT_ADDRESS: String = "0x8b27fa2987630a1acd8d868ba84b2928de737bc2"
        const val OWNER_ADDRESS: String = "0x5d8766ac0075bdf81b48f0bfcf92449e9def0f37"
        const val PASSWORD: String = "1234!"
        const val VERSION: String = "v2.1"
    }
}
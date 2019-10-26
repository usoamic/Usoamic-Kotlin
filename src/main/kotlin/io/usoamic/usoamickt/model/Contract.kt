package io.usoamic.usoamickt.model

import io.usoamic.usoamickt.enum.NetworkType

object Contract {
    fun forNetwork(networkType: NetworkType): String {
        return when(networkType) {
            NetworkType.MAINNET -> "0x5e8a925ef8bceeb7d3fde6fedaeb374dcf39e69f"
            NetworkType.TESTNET -> "0x5e8a925ef8bceeb7d3fde6fedaeb374dcf39e69f"
        }
    }
}
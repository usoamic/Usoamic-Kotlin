package io.usoamic.usoamickt.enum

import io.usoamic.usoamickt.exception.NodeProviderNotFound

enum class NodeProvider {
    INFURA,
    MYETHERWALLET,
    ETHERSCAN;

    fun getUrl(networkType: NetworkType): String {
        return when(this) {
            INFURA -> when(networkType) {
                NetworkType.MAINNET -> "https://mainnet.infura.io:443"
                NetworkType.TESTNET -> "https://rinkeby.infura.io:443"
            }
            MYETHERWALLET -> when(networkType) {
                NetworkType.MAINNET -> "https://api.myetherwallet.com:443/eth"
                NetworkType.TESTNET -> throw NodeProviderNotFound(this, networkType)
            }
            ETHERSCAN -> when(networkType) {
                NetworkType.MAINNET -> "https://etherscan.io:443"
                NetworkType.TESTNET -> "https://rinkeby.etherscan.io:443/api"
            }
        }
    }
}
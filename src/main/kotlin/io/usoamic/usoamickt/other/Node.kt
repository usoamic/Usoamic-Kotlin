package io.usoamic.usoamickt.other

import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import io.usoamic.usoamickt.exception.NodeProviderNotFound

object Node {
    fun by(networkType: NetworkType, nodeProvider: NodeProvider): String {
        return when(nodeProvider) {
            NodeProvider.INFURA -> when(networkType) {
                NetworkType.MAINNET -> "https://mainnet.infura.io:443"
                NetworkType.TESTNET -> "https://rinkeby.infura.io:443"
            }
            NodeProvider.MYETHERWALLET -> when(networkType) {
                NetworkType.MAINNET -> "https://api.myetherwallet.com:443/eth"
                NetworkType.TESTNET -> throw NodeProviderNotFound(nodeProvider, networkType)
            }
            NodeProvider.ETHERSCAN -> when(networkType) {
                NetworkType.MAINNET -> "https://etherscan.io:443"
                NetworkType.TESTNET -> "https://rinkeby.etherscan.io:443/api"
            }
        }
    }
}
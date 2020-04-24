package io.usoamic.usoamickt.other

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.exception.NodeProviderNotFound

object Node {
    fun by(networkType: NetworkType, nodeProvider: NodeProvider): String {
        return when(nodeProvider) {
            is NodeProvider.Infura -> when(networkType) {
                NetworkType.MainNet -> "https://mainnet.infura.io:443/v3/${nodeProvider.projectId}"
                NetworkType.TestNet -> "https://rinkeby.infura.io:443/v3/${nodeProvider.projectId}"
            }
            NodeProvider.MyEtherWallet -> when(networkType) {
                NetworkType.MainNet -> "https://api.myetherwallet.com:443/eth"
                NetworkType.TestNet -> throw NodeProviderNotFound(nodeProvider, networkType)
            }
            NodeProvider.EtherScan -> when(networkType) {
                NetworkType.MainNet -> "https://etherscan.io:443"
                NetworkType.TestNet -> "https://rinkeby.etherscan.io:443/api"
            }
        }
    }
}
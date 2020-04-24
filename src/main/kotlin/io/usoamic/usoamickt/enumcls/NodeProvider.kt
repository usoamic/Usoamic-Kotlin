package io.usoamic.usoamickt.enumcls

sealed class NodeProvider {
    data class Infura(val projectId: String, val projectSecret: String = "") : NodeProvider()
    object MyEtherWallet : NodeProvider()
    object EtherScan : NodeProvider()

    companion object {
        fun valueOf(provider: String, args: String): NodeProvider = when (provider.toUpperCase()) {
            "INFURA" -> Infura(args)
            "MYETHERWALLET" -> MyEtherWallet
            "ETHERSCAN" -> EtherScan
            else -> throw ClassNotFoundException()
        }
    }
}
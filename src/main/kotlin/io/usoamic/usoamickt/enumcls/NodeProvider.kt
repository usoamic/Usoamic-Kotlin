package io.usoamic.usoamickt.enumcls

sealed class NodeProvider {
    data class Infura(val projectId: String, val projectSecret: String = "") : NodeProvider()
    object MyEtherWallet : NodeProvider()
    object EtherScan : NodeProvider()
}
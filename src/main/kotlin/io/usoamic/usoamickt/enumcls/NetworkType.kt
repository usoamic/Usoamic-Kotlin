package io.usoamic.usoamickt.enumcls

sealed class NetworkType {
    object Mainnet : NetworkType()
    object Testnet : NetworkType()

    companion object {
        fun valueOf(type: String): NetworkType = when(type.toUpperCase()) {
            "MAINNET" -> Mainnet
            "TESTNET" -> Testnet
            else -> throw ClassNotFoundException()
        }
    }
}
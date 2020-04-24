package io.usoamic.usoamickt.enumcls

sealed class NetworkType {
    object MainNet : NetworkType()
    object TestNet : NetworkType()

    companion object {
        fun valueOf(type: String): NetworkType = when(type.toUpperCase()) {
            "MAINNET" -> MainNet
            "TESTNET" -> TestNet
            else -> throw ClassNotFoundException()
        }
    }
}
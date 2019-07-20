package io.usoamic.cli.util

import java.security.SecureRandom

open class MnemonicUtils : org.web3j.crypto.MnemonicUtils() {
    companion object {
        private val secureRandom = SecureRandom()
        fun generateMnemonic(): String {
            val initialEntropy = ByteArray(16)
            secureRandom.nextBytes(initialEntropy)
            return generateMnemonic(initialEntropy)
        }
    }
}
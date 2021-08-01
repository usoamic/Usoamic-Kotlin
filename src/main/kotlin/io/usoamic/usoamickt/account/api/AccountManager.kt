package io.usoamic.usoamickt.account.api

interface AccountManager {
    fun importPrivateKey(
        password: String,
        privateKey: String
    ): String

    fun importMnemonic(
        password: String,
        mnemonic: String
    ): String
}
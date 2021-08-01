package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.AccountManager
import io.usoamic.usoamickt.core.Usoamic

open class AccountManagerImpl(
    private val usoamic: Usoamic
) : AccountManager {
    override fun importPrivateKey(password: String, privateKey: String): String {
        return usoamic.importPrivateKey(password, privateKey)
    }

    override fun importMnemonic(password: String, mnemonic: String): String {
        return usoamic.importMnemonic(password, mnemonic)
    }
}
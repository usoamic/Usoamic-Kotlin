package io.usoamic.usoamickt.account.impl.core

import io.usoamic.usoamickt.account.api.TransactionExplorer
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.model.Transaction
import java.math.BigInteger

open class TransactionExplorerImpl constructor(
    private val usoamic: Usoamic
) : PurchasesImpl(usoamic),
    TransactionExplorer {
    override fun getTransaction(txId: BigInteger): Transaction {
        return usoamic.getTransaction(txId)
    }

    override fun getTransactionByAddress(
        owner: String,
        txId: BigInteger
    ): Transaction {
        return usoamic.getTransactionByAddress(
            owner = owner,
            txId = txId
        )
    }

    override fun getNumberOfTransactions(): BigInteger? {
        return usoamic.getNumberOfTransactions()
    }

    override fun getNumberOfTransactionsByAddress(owner: String): BigInteger? {
        return usoamic.getNumberOfTransactionsByAddress(owner)
    }

}
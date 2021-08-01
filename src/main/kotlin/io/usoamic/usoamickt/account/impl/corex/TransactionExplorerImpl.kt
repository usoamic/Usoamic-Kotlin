package io.usoamic.usoamickt.account.impl.corex

import io.usoamic.usoamickt.account.api.TransactionExplorer
import io.usoamic.usoamickt.corex.Usoamic
import io.usoamic.usoamickt.model.Transaction
import java.math.BigInteger

open class TransactionExplorerImpl constructor(
    fileName: String,
    filePath: String,
    private val usoamic: Usoamic
) : PurchasesImpl(
    fileName = fileName,
    filePath = filePath,
    usoamic = usoamic
),
    TransactionExplorer {
    override fun getTransaction(txId: BigInteger): Transaction {
        return usoamic.getTransaction(
            addressOfRequester = address,
            txId = txId
        )
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
        return usoamic.getNumberOfTransactions(
            addressOfRequester = address
        )
    }

    override fun getNumberOfTransactionsByAddress(owner: String): BigInteger? {
        return usoamic.getNumberOfTransactionsByAddress(owner)
    }
}
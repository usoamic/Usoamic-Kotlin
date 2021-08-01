package io.usoamic.usoamickt.account.api

import io.usoamic.usoamickt.model.Transaction
import java.math.BigInteger

interface TransactionExplorer {
    fun getTransaction(txId: BigInteger): Transaction

    fun getTransactionByAddress(
        owner: String,
        txId: BigInteger
    ): Transaction

    fun getNumberOfTransactions(): BigInteger?

    fun getNumberOfTransactionsByAddress(owner: String): BigInteger?
 }
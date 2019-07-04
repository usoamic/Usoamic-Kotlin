package io.usoamic.cli.model

import io.usoamic.cli.util.Coin
import java.math.BigDecimal
import java.math.BigInteger

data class Transaction constructor(
    val isExist: Boolean,
    val txId: BigInteger,
    val from: String,
    val to: String,
    val value: BigDecimal,
    val timestamp: BigInteger
) {
    class Builder {
        private var isExist: Boolean = false
        private lateinit var txId: BigInteger
        private lateinit var from: String
        private lateinit var to: String
        private lateinit var value: BigInteger
        private lateinit var timestamp: BigInteger

        fun setExist(isExist: Boolean) = apply {
            this.isExist = isExist
        }

        fun setTxId(txId: BigInteger) = apply {
            this.txId = txId
        }

        fun setFrom(from: String) = apply {
            this.from = from
        }

        fun setTo(to: String) = apply {
            this.to = to
        }

        fun setValue(value: BigInteger) = apply {
            this.value = value
        }

        fun setTimestamp(timestamp: BigInteger) = apply {
            this.timestamp = timestamp
        }

        fun build() = Transaction(
            isExist,
            txId,
            from,
            to,
            Coin.fromSat(value).toBigDecimal(),
            timestamp
        )
    }
}
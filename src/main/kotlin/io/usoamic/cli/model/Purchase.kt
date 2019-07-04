package io.usoamic.cli.model

import java.math.BigInteger
//bool exist, uint256 id, string purchaseId, string appId, uint256 cost, uint256 timestamp

data class Purchase constructor(
    val isExist: Boolean,
    val id: BigInteger,
    val purchaseId: String,
    val appId: String,
    val cost: BigInteger,
    val timestamp: BigInteger
) {
    class Builder {
        private var isExist: Boolean = false
        private lateinit var id: BigInteger
        private lateinit var purchaseId: String
        private lateinit var appId: String
        private lateinit var cost: BigInteger
        private lateinit var timestamp: BigInteger

        fun setIsExist(isExist: Boolean) = apply {
            this.isExist = isExist
        }

        fun setId(id: BigInteger) = apply {
            this.id = id
        }

        fun setPurchaseId(purchaseId: String) = apply {
            this.purchaseId = purchaseId
        }

        fun setAppId(appId: String) = apply {
            this.appId = appId
        }

        fun setCost(cost: BigInteger) = apply {
            this.cost = cost
        }

        fun setTimestamp(timestamp: BigInteger) = apply {
            this.timestamp = timestamp
        }
    }
}
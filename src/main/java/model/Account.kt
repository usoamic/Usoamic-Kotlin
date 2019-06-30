package model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Account(
    @SerializedName("address")
    private val address: String,
    @SerializedName("path")
    private val path: String,
    @SerializedName("name")
    private val name: String,
    @SerializedName("timestamp")
    private val timestamp: BigInteger
) {
    companion object {
        fun fromJson(json: String): Account {
            return Gson().fromJson(json, Account::class.java)
        }
    }
}
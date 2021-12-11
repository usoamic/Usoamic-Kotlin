package io.usoamic.usoamickt.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.usoamic.usoamickt.util.AccountUtils
import java.io.File
import java.math.BigInteger

data class Account(
    @SerializedName("address")
    val address: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("timestamp")
    val timestamp: BigInteger
) {
    val file: File
        get() = AccountUtils.initFile(path, name)

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun parse(json: String): Account {
            return Gson().fromJson(json, Account::class.java)
        }
    }
}
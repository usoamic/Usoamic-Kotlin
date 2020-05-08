package io.usoamic.usoamickt.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
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
        get() = initFile(path, name)

    companion object {
        const val FILENAME: String = "account.json"

        fun fromJson(json: String): Account {
            return Gson().fromJson(json, Account::class.java)
        }

        fun initFile(filePath: String, fileName: String): File = File(
            if (filePath.isEmpty()) fileName else "$filePath${File.separator}$fileName"
        )
    }
}
package io.usoamic.usoamickt.util

import java.io.File
import java.io.FileWriter

object Files {
    fun writeString(file: File, str: String) {
        FileWriter(file).use {
            it.write(str)
        }
    }

    fun readString(file: File): String {
        return org.web3j.utils.Files.readString(file)
    }
}
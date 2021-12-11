package io.usoamic.usoamickt.util

import java.io.File

object AccountUtils {
    fun initFile(
        filePath: String,
        fileName: String
    ): File {
        return File(
            if (filePath.isEmpty()) fileName else "$filePath${File.separator}$fileName"
        )
    }
}
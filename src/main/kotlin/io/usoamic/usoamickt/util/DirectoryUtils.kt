package io.usoamic.usoamickt.util

import java.io.File

object DirectoryUtils {
    const val DIRECTORY = "Usoamic"
    const val LINUX_DIRECTORY = ".usoamic"

    fun getDefaultKeyDirectory(): String {
        return getDefaultKeyDirectory(System.getProperty("os.name"))
    }

    fun getDefaultKeyDirectory(osName1: String): String {
        val osName = osName1.toLowerCase()
        return when {
            osName.startsWith("mac") -> {
                String.format(
                    "%s%sLibrary%s$DIRECTORY",
                    System.getProperty("user.home"), File.separator, File.separator
                )
            }
            osName.startsWith("win") -> {
                String.format("%s%s$DIRECTORY", System.getenv("APPDATA"), File.separator)
            }
            else -> {
                String.format(
                    "%s%s$LINUX_DIRECTORY",
                    System.getProperty("user.home"),
                    File.separator
                )
            }
        }
    }

    fun getTestnetKeyDirectory(): String {
        return String.format(
            "%s%stestnet%skeystore",
            getDefaultKeyDirectory(),
            File.separator,
            File.separator
        )
    }

    fun getMainnetKeyDirectory(): String {
        return String.format(
            "%s%skeystore",
            getDefaultKeyDirectory(),
            File.separator
        )
    }
}
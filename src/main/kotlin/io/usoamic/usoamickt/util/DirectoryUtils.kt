package io.usoamic.usoamickt.util

import java.io.File

object DirectoryUtils {
    private const val DIRECTORY = "Usoamic"
    private const val LINUX_DIRECTORY = ".usoamic"

    @JvmStatic
    fun getDefaultKeyDirectory(): String {
        return getDefaultKeyDirectory(System.getProperty("os.name"))
    }

    @JvmStatic
    fun getTestnetKeyDirectory(): String {
        return String.format(
            "%s%stestnet%skeystore",
            getDefaultKeyDirectory(),
            File.separator,
            File.separator
        )
    }

    @JvmStatic
    fun getMainnetKeyDirectory(): String {
        return String.format(
            "%s%skeystore",
            getDefaultKeyDirectory(),
            File.separator
        )
    }

    private fun getDefaultKeyDirectory(osName1: String): String {
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
}
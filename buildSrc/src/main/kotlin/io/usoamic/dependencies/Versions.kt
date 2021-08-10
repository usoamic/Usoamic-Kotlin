package io.usoamic.dependencies

object Versions {
    private const val isAndroid = false

    object Usoamic {
        const val validateUtilKt = "1.0.1-3"
    }

    object Java {
        const val javaxAnnotationApi = "1.3.2"
    }

    object DI {
        const val dagger = "2.37"
        const val daggerCompiler = "2.37"
    }

    object Cryptocurrency {
        val web3j get() = if (isAndroid) "4.6.0-android" else "4.6.0"
    }

    object Test {
        const val kotlinTestJunit = "1.5.21"
        const val junitJupiter = "5.5.0"
    }

    object Other {
        const val gson = "2.8.5"
    }
}
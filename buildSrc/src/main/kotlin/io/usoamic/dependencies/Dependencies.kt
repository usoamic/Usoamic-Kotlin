package io.usoamic.dependencies

object Dependencies {
    object Usoamic {
        const val validateUtilKt = "com.github.usoamic:validateutilkt:v${Versions.Usoamic.validateUtilKt}"
    }

    object Java {
        const val javaxAnnotationApi = "javax.annotation:javax.annotation-api:${Versions.Java.javaxAnnotationApi}"
    }

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
        const val stdLibJDK = "org.jetbrains.kotlin:kotlin-stdlib"
        const val stdLibJDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    }

    object DI {
        const val dagger = "com.google.dagger:dagger:${Versions.DI.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.DI.daggerCompiler}"
    }

    object Cryptocurrency {
        val web3j get() = "org.web3j:core:${Versions.Cryptocurrency.web3j}"
    }

    object Test {
        const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit5:${Versions.Test.kotlinTestJunit}"
        const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.Test.junitJupiter}"
    }

    object Other {
        const val gson = "com.google.code.gson:gson:${Versions.Other.gson}"
    }
}
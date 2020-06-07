import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Version {
    const val kotlinStdLib = "1.3.50"
    const val dagger = "2.27"
    const val gson = "2.8.5"
    const val web3j = "4.6.0"
    const val validateUtilKt = "16304cc35d"
    const val javaxAnnotationApi = "1.3.2"
    const val daggerCompiler = "2.27"
    const val kotlinTestJunit5 = "1.3.50"
    const val junitJupiter = "5.5.0"
}

plugins {
    java
    maven
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

allprojects {
    group = "io.usoamic"
    version = "1.2.1"
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compile("org.jetbrains.kotlin", "kotlin-stdlib", Version.kotlinStdLib)
    compile("com.google.dagger", "dagger", Version.dagger)
    compile("com.google.code.gson", "gson", Version.gson)
    compile("org.web3j", "core", Version.web3j)
    compile("com.github.usoamic", "validateutilkt", Version.validateUtilKt)
    compile("javax.annotation", "javax.annotation-api", Version.javaxAnnotationApi)
    kapt("com.google.dagger", "dagger-compiler", Version.daggerCompiler)

    testCompile("org.jetbrains.kotlin", "kotlin-test-junit5", Version.kotlinTestJunit5)
    testCompile("org.junit.jupiter", "junit-jupiter", Version.junitJupiter)
    testCompile("javax.annotation", "javax.annotation-api", Version.javaxAnnotationApi)
    kaptTest("com.google.dagger", "dagger-compiler", Version.daggerCompiler)
    testAnnotationProcessor("com.google.dagger", "dagger-compiler", Version.daggerCompiler)
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}
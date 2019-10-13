import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val VERSION = "1.0.7"

plugins {
    java
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "10"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.50")
    compile("com.google.dagger", "dagger", "2.23.2")
    compile("com.google.code.gson", "gson", "2.8.5")
    compile("org.web3j", "core", "4.3.1")
    kapt("com.google.dagger", "dagger-compiler", "2.23.2")

    testCompile("org.jetbrains.kotlin", "kotlin-test-junit5", "1.3.40")
    testCompile("org.junit.jupiter", "junit-jupiter", "5.5.0")
    testCompile("com.google.dagger", "dagger", "2.23.2")
    kaptTest("com.google.dagger", "dagger-compiler", "2.23.2")
    testAnnotationProcessor("com.google.dagger", "dagger-compiler", "2.23.2")
}
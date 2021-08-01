import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.usoamic.dependencies.Dependencies

plugins {
    java
    kotlin("jvm") version "1.5.20"
    kotlin("kapt") version "1.5.20"
}

allprojects {
    group = "io.usoamic"
    version = "1.3.0"
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    /*
     * Implementation
     */
    implementation(Dependencies.Kotlin.stdLibJDK)
    implementation(Dependencies.Kotlin.stdLibJDK8)
    implementation(Dependencies.DI.dagger)
    implementation(Dependencies.DI.daggerCompiler)
    implementation(Dependencies.Other.gson)
    implementation(Dependencies.Cryptocurrency.web3j)
    implementation(Dependencies.Usoamic.validateUtilKt)
    implementation(Dependencies.Java.javaxAnnotationApi)

    /*
     * Kapt
     */
    kapt(Dependencies.DI.daggerCompiler)

    /*
     * Test Implementation
     */
    testImplementation(Dependencies.Cryptocurrency.web3j)
    testImplementation(Dependencies.Test.kotlinTestJunit)
    testImplementation(Dependencies.Test.junitJupiter)
    testImplementation(Dependencies.Java.javaxAnnotationApi)

    /*
     * Kapt Test
     */
    kaptTest(Dependencies.DI.daggerCompiler)

    /*
     * Test Annotation Processor
     */
    testAnnotationProcessor(Dependencies.DI.daggerCompiler)
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

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
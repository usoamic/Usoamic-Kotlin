import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Version {
    private const val isAndroid = true
    const val dagger = "2.27"
    const val gson = "2.8.5"
    val web3j get() = if(isAndroid) "4.6.0-android" else "4.6.0"
    const val validateUtilKt = "1.0.0"
    const val javaxAnnotationApi = "1.3.2"
    const val daggerCompiler = "2.27"
    const val kotlinTestJunit5 = "1.4.32"
    const val junitJupiter = "5.5.0"
}

plugins {
    java
    maven
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.32"
}

allprojects {
    group = "io.usoamic"
    version = "1.2.2"
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.dagger", "dagger", Version.dagger)
    implementation("com.google.code.gson", "gson", Version.gson)
    api("org.web3j", "core", Version.web3j)
    api("com.github.usoamic", "validateutilkt", Version.validateUtilKt)
    implementation("javax.annotation", "javax.annotation-api", Version.javaxAnnotationApi)
    kapt("com.google.dagger", "dagger-compiler", Version.daggerCompiler)

    testImplementation("org.web3j", "core", Version.web3j)
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5", Version.kotlinTestJunit5)
    testImplementation("org.junit.jupiter", "junit-jupiter", Version.junitJupiter)
    testImplementation("javax.annotation", "javax.annotation-api", Version.javaxAnnotationApi)
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
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Version {
    const val dagger = "2.27"
    const val gson = "2.8.5"
    const val web3j = "4.6.0"
    const val javaxAnnotationApi = "1.3.2"
    const val daggerCompiler = "2.27"
    const val kotlinTestJunit5 = "1.3.50"
    const val junitJupiter = "5.5.0"
}

plugins {
    java
    maven
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
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
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.dagger", "dagger", Version.dagger)
    implementation("com.google.code.gson", "gson", Version.gson)
    implementation("org.web3j", "core", Version.web3j)
    implementation("javax.annotation", "javax.annotation-api", Version.javaxAnnotationApi)
    kapt("com.google.dagger", "dagger-compiler", Version.daggerCompiler)

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
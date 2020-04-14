import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    maven
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

allprojects {
    group = "io.usoamic"
    version = "1.1.5"
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.50")
    compile("com.google.dagger", "dagger", "2.27")
    compile("com.google.code.gson", "gson", "2.8.5")
    compile("org.web3j", "core", "4.3.1")
    compile("javax.annotation", "javax.annotation-api", "1.3.2")
    kapt("com.google.dagger", "dagger-compiler", "2.27")

    testCompile("org.jetbrains.kotlin", "kotlin-test-junit5", "1.3.50")
    testCompile("org.junit.jupiter", "junit-jupiter", "5.5.0")
    testCompile("javax.annotation", "javax.annotation-api", "1.3.2")
    kaptTest("com.google.dagger", "dagger-compiler", "2.27")
    testAnnotationProcessor("com.google.dagger", "dagger-compiler", "2.27")
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
[![](https://jitpack.io/v/usoamic/Usoamic-Kotlin.svg)](https://jitpack.io/#usoamic/Usoamic-Kotlin)
# Usoamic-Kotlin

Kotlin library for interaction with Usoamic contract

## Dependencies
```gradle
dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    implementation 'com.google.dagger:dagger:2.23.2'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'org.web3j:core:4.3.1'
    kapt 'com.google.dagger:dagger-compiler:2.23.2'

    testImplementation 'org.jetbrains.kotlin:kotlin-test-junit5:1.3.40'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.5.0'
    testImplementation "com.google.dagger:dagger:2.23.2"
    kaptTest 'com.google.dagger:dagger-compiler:2.23.2'
    testAnnotationProcessor "com.google.dagger:dagger-compiler:2.23.2"
}

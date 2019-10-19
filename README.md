[![](https://jitpack.io/v/usoamic/usoamic.kt.svg)](https://jitpack.io/#usoamic/usoamic.kt)
# usoamic.kt

Kotlin library for interaction with Usoamic contract

## Gradle Setup
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.usoamic:usoamickt:v1.0.11'
}
```

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

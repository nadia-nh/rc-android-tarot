import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.3.0"
}

val keystorePropertiesFile = rootProject.file("local.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.flowworks.arcanaflux"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.flowworks.arcanaflux"
        minSdk = 24
        targetSdk = 37
        versionCode = 4
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = keystoreProperties["RELEASE_STORE_FILE"]?.let { file(it.toString()) }
            storePassword = keystoreProperties["RELEASE_STORE_PASSWORD"]?.toString()
            keyAlias = keystoreProperties["RELEASE_KEY_ALIAS"]?.toString()
            keyPassword = keystoreProperties["RELEASE_KEY_PASSWORD"]?.toString()
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.foundation)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Room components
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.runtime)
    androidTestImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Material 3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material3)

    // Extra icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Retrofit for API calls
    implementation(libs.retrofit)

    // Kotlin Serialization for JSON parsing
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0" // Подключаем плагин kotlinx.serialization
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35 // Увеличьте до 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 35 // Можно также обновить до 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    // Основные зависимости Compose и AndroidX
    implementation(libs.ui) // Совместима с другими библиотеками Compose
    implementation(libs.androidx.material) // Совместима с другими библиотеками Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // Зависимости Ktor (обновите до последних версий)
    implementation(libs.ktor.client.core.v232) // Последняя версия на момент написания
    implementation(libs.ktor.client.cio.v232) // Последняя версия на момент написания
    implementation(libs.ktor.client.content.negotiation) // Последняя версия на момент написания
    implementation(libs.ktor.serialization.kotlinx.json) // Последняя версия на момент написания

    implementation(libs.kotlinx.serialization.json.v151) // Совместимая версия для Ktor 2.3.x

    // Зависимости для тестирования
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit) // Правильная зависимость для JUnit
    androidTestImplementation(libs.androidx.espresso.core) // Правильная зависимость для Espresso
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}


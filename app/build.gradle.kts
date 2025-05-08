plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsKotlinKapt)
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")

}

android {
    namespace = "com.example.ultimate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ultimate"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.navigation.compose)


    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.navigation.material)


    implementation(libs.androidx.compose.preview)

    implementation(libs.coil.compose)


    implementation(libs.androidx.icons)
    implementation(libs.androidx.compose.foundation)


    //Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.accompanist.systemuicontroller)


    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.com.json)
    implementation(libs.retrofit.coroutines.adapter)

    implementation(libs.lottie.compose)

    implementation (libs.androidx.material3.window.size)

    implementation(libs.androidx.constraintlayout.compose)


    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)  // For Kotlin extensions and Coroutine support
    kapt(libs.room.compiler)      // Annotation processor

}
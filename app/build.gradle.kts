plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") // 確保這行存在
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.test.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.demo"
        minSdk = 24
        targetSdk = 34
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
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version") // Java 使用
    kapt("androidx.room:room-compiler:$room_version") // Kotlin 使用

    // 協程支援
    implementation("androidx.room:room-ktx:$room_version")

    // 測試
    testImplementation("androidx.room:room-testing:$room_version")

    //
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // 首先添加依赖
    implementation("cafe.adriel.voyager:voyager-androidx:1.0.0-rc03") // Voyager ViewModel 插件


    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // 首先添加依赖
    implementation("cafe.adriel.voyager:voyager-androidx:1.0.0-rc03") // Voyager ViewModel 插件
    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc03")
    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0-rc03")
    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:1.0.0-rc03")


}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
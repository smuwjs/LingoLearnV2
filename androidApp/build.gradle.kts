plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.application")
    alias(libs.plugins.kotlin.plugin.compose)
}

android {
    namespace = "com.lingolearn.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lingolearn.app"
        minSdk = 24
        targetSdk = 34
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.kotlinx.coroutines.android)
    debugImplementation(libs.compose.ui.tooling)
}

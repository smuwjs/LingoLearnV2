import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin.compose)
}

kotlin {
    androidTarget()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.koin.android)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.graphics)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.material3)
                implementation(libs.compose.navigation)
            }
        }
    }
}

android {
    namespace = "com.lingolearn.app.shared"
    compileSdk = 34
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

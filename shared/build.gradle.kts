import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin.compose)
}

kotlin {
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets.all {
        languageSettings.optIn("kotlin.ExperimentalStdlibApi")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.graphics)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.material3)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation("androidx.navigation:navigation-compose:2.5.3")
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotest.framework)
                implementation(libs.kotest.assertions)
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

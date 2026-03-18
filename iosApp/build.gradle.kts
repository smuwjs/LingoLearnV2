plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared"))
            }
        }
        iosMain {
        }
    }
}

android {
    namespace = "com.lingolearn.app.ios"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lingolearn.app.ios"
        minSdk = 24
        targetSdk = 34
    }
}

tasks.register("buildIosApp", Exec::class) {
    workingDir = project.projectDir
    commandLine = "xcodebuild"
    args = listOf(
        "-scheme", "iosApp",
        "-configuration", "Debug",
        "-destination", "platform=iOS Simulator,name=iPhone 15",
        "build"
    )
}

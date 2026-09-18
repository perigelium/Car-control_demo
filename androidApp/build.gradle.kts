import org.jetbrains.kotlin.gradle.dsl.*

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
}

android {
    namespace = "eu.vctrl4"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        applicationId = "eu.vctrl4"
        versionCode = 1
        versionName = "1.0.204"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

	buildTypes {
		getByName("debug") {
			project.extra.set("is_debug_mode", true)
		}
		getByName("release") {
			project.extra.set("is_debug_mode", false)
		}
	}
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
}

dependencies {
    implementation(project(":sharedUI"))
    implementation(libs.androidx.activityCompose)
	implementation(libs.koin.android)
}

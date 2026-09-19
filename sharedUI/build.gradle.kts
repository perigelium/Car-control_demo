


import com.codingfeline.buildkonfig.compiler.*
import org.jetbrains.kotlin.gradle.dsl.*

val isDebugBuild = gradle.startParameter.taskNames.any { it.contains("debug", ignoreCase = true) }

plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.compose.multiplatform)
	alias(libs.plugins.android.kmp.library)
	alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.room)
	alias(libs.plugins.ksp)
	alias(libs.plugins.build.config)
}

kotlin {
	androidTarget {
		compilerOptions { jvmTarget = JvmTarget.JVM_17 }
	}

	iosArm64()
	iosSimulatorArm64()

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
            // Fixes the bundle identifier layout warning
            freeCompilerArgs += listOf("-Xbinary=bundleId=eu.vctrl4.iosApp.sharedUI")

            // Instructs the Kotlin Native compiler to expect FirebaseMessaging
            linkerOpts("-framework", "FirebaseMessaging")
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
            freeCompilerArgs += listOf("-Xbinary=bundleId=eu.vctrl4.iosApp.sharedUI")
            isStatic = true
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
		if (konanTarget.family.isAppleFamily) {
			binaries.framework {
				linkerOpts("-framework", "MapKit")
			}
		}
	}

	sourceSets {
		commonMain.dependencies {
			api(libs.compose.runtime)
			api(libs.compose.ui)
			api(libs.compose.foundation)
			api(libs.compose.resources)
			api(libs.compose.ui.tooling.preview)
			api(libs.compose.material3)

			implementation(libs.kotlinx.coroutines.core)

			implementation(libs.ktor.client.core)
			implementation(libs.ktor.client.content.negotiation)
			implementation(libs.ktor.client.serialization)
			implementation(libs.ktor.serialization.json)
			implementation(libs.ktor.client.logging)
			implementation(libs.ktor.client.mock)

			implementation(libs.kotlinx.serialization.json)
			implementation(libs.kotlinx.datetime)
			implementation(libs.room.runtime)

			implementation(libs.koin.core)
			implementation(libs.koin.compose.viewmodel)
			implementation(libs.koin.compose)

			implementation(libs.korlibs.crypto)

			implementation(libs.navigation.compose)

			implementation(libs.compose.icons.core)
			implementation(libs.sqlite.bundled)
			implementation(libs.androidx.datastore.preferences)
			implementation(libs.androidx.datastore.preferences.core)

			implementation(libs.jetbrains.lifecycle.runtime.compose)
		}

		commonTest.dependencies {
			implementation(kotlin("test"))
			implementation(libs.compose.ui.test)
			implementation(libs.kotlinx.coroutines.test)
		}

		androidMain.dependencies {
			implementation(libs.kotlinx.coroutines.android)
			implementation(libs.ktor.client.okhttp)

			implementation(libs.androidx.sqlite)
			implementation(libs.osmdroid.android)
		}

		iosMain.dependencies {
			implementation(libs.ktor.client.darwin)
		}
	}
}

dependencies {
	debugImplementation(libs.compose.ui.tooling)
}

android {
	namespace = "eu.vctrl4"
	compileSdk = 36
	defaultConfig {
		minSdk = 23
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

room {
	schemaDirectory("$projectDir/schemas")
}

dependencies {
	with(libs.room.compiler) {
		add("kspAndroid", this)
		add("kspIosArm64", this)
		add("kspIosSimulatorArm64", this)
	}

	buildkonfig {
		packageName = "eu.vctrl4"

		val isDebug = isDebugBuild || (project.extra.has("is_debug_mode") &&
				project.extra.get("is_debug_mode") == true)

		defaultConfigs {
			buildConfigField(FieldSpec.Type.INT, "VERSION_CODE", "206")
			buildConfigField(FieldSpec.Type.STRING, "VERSION_NAME", "3")

			buildConfigField(FieldSpec.Type.BOOLEAN, "DEBUG", isDebug.toString())
			buildConfigField(FieldSpec.Type.STRING, "APP_LOG_TAG", "vctrl4")

			buildConfigField(FieldSpec.Type.STRING, "HOST_WIALON", "hst-api.wialon.com")

			buildConfigField(
				FieldSpec.Type.STRING,
				"HOST_MAP_SEARCH_NOMINATIM_OSM",
				"\"https://nominatim.openstreetmap.org/\""
			                )

			buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "https://backend.vctrl.eu/api/v1.0/")
			buildConfigField(FieldSpec.Type.STRING, "API_CAR_CONTROL_URL", "https://carcontrol.vctrl.eu/")
		}

		targetConfigs {
			create("android") {
				buildConfigField(FieldSpec.Type.INT, "minSdkVersion", "23")
				buildConfigField(
					FieldSpec.Type.INT, "targetSdkVersion", "36"
				                )
				buildConfigField(
					FieldSpec.Type.STRING,
					"GOOGLE_PLAY_STORE_APPS_PREFIX",
					"https://play.google.com/store/apps/details?id="
				                )
			}
		}

	}

}








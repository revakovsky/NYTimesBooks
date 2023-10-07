plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = libs.versions.name.data.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://api.giphy.com/v1/gifs/\"")
            buildConfigField(type = "String", name = "API_KEY", value = "\"3FQrv9Cok9ki6pwej7g8G0l0LyJqQwV9\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://api.giphy.com/v1/gifs/\"")
            buildConfigField(type = "String", name = "API_KEY", value = "\"3FQrv9Cok9ki6pwej7g8G0l0LyJqQwV9\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Modules
    implementation(project(path = ":domain"))

    // Android
    implementation(libs.android.coreKtx)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // Network
    implementation(libs.bundles.okHttp)
    implementation(libs.bundles.retrofit2)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Dagger2
    implementation(libs.dagger2)
    ksp(libs.dagger2.compiler)

}
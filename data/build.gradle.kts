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

            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://api.nytimes.com/svc/books/v3/\"")
            buildConfigField(type = "String", name = "API_KEY", value = "\"JNd7LkmFEYe6WelU5YG2FwZAXAeGCAH4\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://api.nytimes.com/svc/books/v3/\"")
            buildConfigField(type = "String", name = "API_KEY", value = "\"JNd7LkmFEYe6WelU5YG2FwZAXAeGCAH4\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        buildConfig = true
    }
    kotlin {
        jvmToolchain(11)
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

    // Coil
    implementation(libs.coil.compose)

}
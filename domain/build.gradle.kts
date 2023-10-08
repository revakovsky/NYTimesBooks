plugins {
    id("kotlin")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
kotlin {
    jvmToolchain(8)
}

dependencies {

    implementation(libs.inject)
    implementation(libs.coroutines.core)

}
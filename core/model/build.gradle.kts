plugins {
    alias(libs.plugins.tedmob.jvm.library)
    id("kotlin")
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
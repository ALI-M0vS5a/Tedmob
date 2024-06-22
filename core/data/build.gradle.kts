plugins {
    alias(libs.plugins.tedmob.android.library)
    alias(libs.plugins.tedmob.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "dev.alimoussa.tedmob.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(projects.core.model)
    api(projects.core.datastore)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}
plugins {
    alias(libs.plugins.tedmob.android.library)
    alias(libs.plugins.tedmob.android.hilt)
}

android {
    namespace = "dev.alimoussa.tedmob.common"

    defaultConfig {
        testApplicationId = "dev.alimoussa.detmob.core.common.test"
        defaultConfig {
            manifestPlaceholders["applicationIdPlaceholder"] = "dev.alimoussa.detmob.core.common"
        }
    }

}

dependencies {
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.kotlinx.datetime)
}
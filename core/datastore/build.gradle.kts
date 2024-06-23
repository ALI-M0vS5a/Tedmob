plugins {
    alias(libs.plugins.tedmob.android.library)
    alias(libs.plugins.tedmob.android.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "dev.alimoussa.tedmob.datastore"
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(libs.androidx.dataStore.core)
    implementation(projects.core.datastoreProto)
    implementation(projects.core.model)
    implementation(projects.core.common)
    testImplementation(libs.kotlinx.coroutines.test)
}
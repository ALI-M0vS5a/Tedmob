plugins {
    alias(libs.plugins.tedmob.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.alimoussa.tedmob.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.network)

    implementation(libs.javax.inject)
}
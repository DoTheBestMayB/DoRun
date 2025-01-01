plugins {
    alias(libs.plugins.dorun.android.library)
    alias(libs.plugins.dorun.jvm.ktor)
}

android {
    namespace = "com.dothebestmayb.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(libs.timber)
    implementation(libs.bundles.koin)
}

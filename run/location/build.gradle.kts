plugins {
    alias(libs.plugins.dorun.android.library)
}

android {
    namespace = "com.dothebestmayb.run.location"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.run.domain)

    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.koin)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
}

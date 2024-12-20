plugins {
    alias(libs.plugins.dorun.android.feature.ui)
}

android {
    namespace = "com.dothebestmayb.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}

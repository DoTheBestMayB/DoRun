plugins {
    alias(libs.plugins.dorun.android.library)
}

android {
    namespace = "com.dothebestmayb.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}

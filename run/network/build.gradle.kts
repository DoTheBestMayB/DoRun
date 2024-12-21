plugins {
    alias(libs.plugins.dorun.android.library)
    alias(libs.plugins.dorun.jvm.ktor)
}

android {
    namespace = "com.dothebestmayb.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}

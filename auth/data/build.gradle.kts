plugins {
    alias(libs.plugins.dorun.android.library)
    alias(libs.plugins.dorun.jvm.ktor)
}

android {
    namespace = "com.dothebestmayb.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}

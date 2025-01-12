plugins {
    alias(libs.plugins.dorun.jvm.libray)
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}

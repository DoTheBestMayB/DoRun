plugins {
    alias(libs.plugins.dorun.android.library)
    alias(libs.plugins.dorun.android.room)
}

android {
    namespace = "com.dothebestmayb.core.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)
}

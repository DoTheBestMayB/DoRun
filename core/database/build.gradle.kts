plugins {
    alias(libs.plugins.dorun.android.library)
}

android {
    namespace = "com.dothebestmayb.core.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.org.mongodb.bson)
}

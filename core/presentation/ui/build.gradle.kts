plugins {
    alias(libs.plugins.dorun.android.library.compose)
}

android {
    namespace = "com.dothebestmayb.core.presentation.ui"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)


    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
}

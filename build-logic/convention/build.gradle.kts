plugins {
    `kotlin-dsl`
}

group = "com.dothebestmayb.dorun.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin) // runTime이 아닌 compileTime에만 동작?
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

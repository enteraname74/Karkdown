plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.koin.core)
}
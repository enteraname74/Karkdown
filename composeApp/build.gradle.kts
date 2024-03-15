import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    
    alias(libs.plugins.jetbrainsCompose)
}

repositories {
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.file.picker)
            implementation(libs.kmd2pdf)

            implementation(libs.koin.compose)
            implementation(libs.koin.core)

            runtimeOnly(libs.androidx.collection)

            implementation(project(":domain"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.github.enteraname74.karkdown"
            packageVersion = "1.0.0"
        }
    }
}

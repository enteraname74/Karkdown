import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvmToolchain(17)
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
            implementation(libs.native.file.chooser)
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Rpm, TargetFormat.AppImage)

            packageName = "Karkdown"
            packageVersion = "0.1.0"
            description = "Markdown file editor software."

            linux {
                packageName = "Karkdown"
                packageVersion = "0.1.0"
                appRelease = "1"
                appCategory = "Development;Markdown"
                rpmLicenseType = "GPL-3.0-or-later"
                iconFile.set(project.file("src/commonMain/composeResources/drawable/icon.png"))
            }
        }
    }
}

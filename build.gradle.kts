plugins {
    // Plugins du catalogue libs
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Google Services plugin — une seule version !
    id("com.google.gms.google-services") version "4.4.2" apply false

        id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false

}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

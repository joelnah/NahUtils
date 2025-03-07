plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "nah.prayer.library"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaVer.get()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)

    implementation(libs.bundles.androidx.datasore)
    implementation (libs.androidx.security.crypto)

    implementation (libs.androidx.runtime)
    implementation (libs.androidx.ui)

    implementation (libs.timber)
    implementation (libs.gson)
    implementation (libs.kotlin.reflect)

}

publishing { // 추가
    publications {
        afterEvaluate {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.joelnah"
                artifactId = "NahUtils"
                version = libs.versions.verName.get()
            }
        }
    }
}
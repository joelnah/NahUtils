plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "nah.prayer.library"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.15.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")

    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")

    implementation ("androidx.compose.runtime:runtime:1.7.5")
    implementation ("androidx.compose.ui:ui:1.7.5")

    implementation ("com.jakewharton.timber:timber:5.0.1")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:2.0.10")
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")
}

publishing { // 추가
    publications {
        afterEvaluate {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.joelnah"
                artifactId = "NahUtils"
                version = "2.1.0-alpha01"
            }
        }
    }
}
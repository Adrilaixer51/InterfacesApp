plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.primetea"

    compileSdk = 34          // usa 34 (recomendado).
    // Si necesitas 35 añade el flag en gradle.properties (ver paso 5).

    defaultConfig {
        applicationId = "com.example.primetea"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    kotlin { jvmToolchain(17) }   // Gradle 8 requiere esto para que KAPT use JDK 17

    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"  // compatible con Kotlin 1.9.22
    }

    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    /* ---------- Compose BOM (mantiene todo alineado) ---------- */
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))

    // Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Navigation-Compose
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Room + kapt
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // NumberPicker, Coil, ViewModel-Compose …
    implementation("com.chargemap.compose:numberpicker:1.0.3")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Core / lifecycle
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    /* ---------- tests ---------- */
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("io.coil-kt:coil-compose:2.5.0")

}

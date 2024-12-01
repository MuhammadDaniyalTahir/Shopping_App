plugins {
    id("com.android.application") // Directly use the plugin id

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shopping_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shopping_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")



    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-firestore")

    // FirebaseUI Libraries
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")

    // Dependency for Firebase REaltime Database
    implementation ("com.google.firebase:firebase-database:19.3.1")
    implementation ("com.google.android.material:material:1.1.0")
}
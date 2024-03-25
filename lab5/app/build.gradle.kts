val room_version = "2.2.5"
val lifecycle_version = "2.2.0"
val arch_version = "2.1.0"

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.lab5"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab5"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions{
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas".toString()
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {


    //room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    androidTestImplementation("androidx.room:room-testing:$room_version")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    androidTestImplementation("androidx.arch.core:core-testing:$arch_version")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
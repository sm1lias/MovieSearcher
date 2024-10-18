plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = "com.smilias.movierama"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.smilias.movierama"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("debug")
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            compose = true
            viewBinding = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.13"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {

        implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")


        implementation("androidx.navigation:navigation-compose:2.6.0")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

        implementation("androidx.paging:paging-compose:3.2.0")
        implementation("androidx.paging:paging-runtime-ktx:3.3.2")

        implementation("io.coil-kt:coil-compose:2.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

        implementation("com.google.dagger:hilt-android:2.48")
        implementation("com.google.android.material:material:1.9.0")
        kapt("com.google.dagger:hilt-android-compiler:2.48")

        implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

        implementation("androidx.datastore:datastore-preferences:1.0.0")

        implementation("androidx.compose.material:material-icons-extended-android:1.6.0-alpha02")
        implementation("androidx.compose.material:material:1.4.3")



        implementation("androidx.core:core-ktx:1.9.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.activity:activity-compose:1.7.0")
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3:1.2.0-alpha04")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")


        implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")

        implementation("io.coil-kt.coil3:coil:3.0.0-rc01")
        implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0-rc01")

        //swipe refresh layout
        implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    }
}
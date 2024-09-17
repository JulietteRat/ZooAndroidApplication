plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ldnr.welovestephane"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ldnr.welovestephane"
        minSdk = 23
        maxSdk = 38
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //implementation("org.xhtmlrenderer:flying-saucer-pdf:9.6.1")
}
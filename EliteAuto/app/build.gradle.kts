plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.eliteauto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eliteauto"
        minSdk = 30
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
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    // OkHttp Logging Interceptor (optionnel)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.swiperefreshlayout)
    implementation(libs.facebook.login)
    implementation(libs.facebook.android.sdk)
    implementation("com.facebook.android:facebook-login:15.0.1")

}
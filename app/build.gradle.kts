import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.detektApplication)
}

android {
    namespace = "ru.astondevs.worldnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.astondevs.worldnews"
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
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(rootProject.file("keys.properties")))

    buildTypes.all {
        buildConfigField("String", "API_ENDPOINT", keystoreProperties["baseUrl"] as String)
        buildConfigField("String", "API_KEY", keystoreProperties["apiKey"] as String)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation(libs.fragment.ktx)

    implementation(libs.viewbindingdelegate)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava3)
    implementation(libs.converter.gson)
    implementation(libs.rxjava3)
    implementation(libs.rxjava3.android)

    implementation(libs.serialization.json)
    implementation(libs.serialization.converter)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    implementation(libs.androidx.core)

    annotationProcessor (libs.dagger.compiler)
    annotationProcessor (libs.dagger.android.processor)

    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)

    implementation(libs.moxy)
    implementation(libs.moxy.androidx)
    annotationProcessor(libs.moxy.compiler)

    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    implementation(libs.swiper)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

detekt {
    source.setFrom(files(projectDir))
    config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
    parallel = true
    reports {
        txt.required.set(false)
        xml.required.set(false)
        sarif.required.set(false)
        html {
            required.set(true)
            outputLocation.set(file("build/reports/detekt.html"))
        }
    }
}

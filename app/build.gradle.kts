import java.util.Properties

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = Configs.applicationId
    compileSdk = Configs.compileSdkVersion

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = Configs.testInstrumentationRunner

        val properties = Properties()
        val openWeatherApiKey: String

        val localPropertiesFile = project.rootProject.file("local.properties")
        openWeatherApiKey = if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
            properties.getProperty("OPEN_WEATHER_API_KEY") ?: ""
        } else {
            System.getenv("OPEN_WEATHER_API_KEY") ?: ""
        }

        buildConfigField(
            "String",
            "OPEN_WEATHER_API_KEY",
            openWeatherApiKey
        )
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")

        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    android {
        sourceSets {
            getByName("test").java.srcDir("src/sharedTest/java")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core-ui"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeToolingPreview)
    implementation(Dependencies.composeLiveData)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.composeActivity)
    implementation(Dependencies.testMonitor)
    testImplementation(Dependencies.junit4)
    androidTestImplementation(Dependencies.testRunner)
    testImplementation(Dependencies.truth)
    testImplementation(Dependencies.mockk)
    androidTestImplementation(Dependencies.junitExtensions)
    androidTestImplementation(Dependencies.espressoCore)
    testImplementation(Dependencies.androidArchCoreTest)
    testImplementation(Dependencies.coroutinesTest)
    androidTestImplementation(Dependencies.composeUiTest)
    debugImplementation(Dependencies.composeTooling)
    coreLibraryDesugaring(Dependencies.coreLibraryDesugaring)
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.locationServices)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitMoshiConverter)
    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.composeViewModel)
    implementation(Dependencies.ktxLiveData)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.composeConstraintLayout)
    implementation(Dependencies.extendedMaterialIcons)
    implementation(Dependencies.datastorePreferences)
    implementation(Dependencies.datastoreCore)
    implementation(Dependencies.flowCombineTuple)
    implementation(Dependencies.stetho)
    implementation(Dependencies.stethoOkHttp3)
    implementation(Dependencies.accompanistSwipeRefresh)
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.kizitonwose.calendar:compose:2.2.1-SNAPSHOT")
    implementation("com.google.accompanist:accompanist-permissions:0.20.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

}
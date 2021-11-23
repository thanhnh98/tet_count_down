object Versions{
    const val kotlin_stdlib_version = "1.5.10"
    const val core_ktx_version = "1.5.0"
    const val appcompat_version = "1.3.0"
    const val material_version = "1.3.0"
    const val constraintlayout_version = "2.0.4"
    const val junit_version = "4.+"
    const val junit_test_version = "1.1.2"
    const val espresso_core_version = "3.3.0"
    const val gradle_version = "4.2.1"

    //Converter
    const val gson_version = "2.8.7"
    const val moshi_version = "1.12.0"

    //okhttp
    const val okhttp_version = "4.9.1"

    //okhttp
    const val retrofit_version = "2.9.0"

    //Facebook
    const val facebook_android_sdk_version = "[4,5)"

    //Google
    const val play_services_auth_version = "19.0.0"

    //Rx
    const val rx_java_version = "3.0.0"
    const val rx_android_version = "3.0.0"

    //Coroutine
    const val kotlinx_coroutines_android_versions = "1.3.9"

    const val glide_version = "4.12.0"

    const val kodein_di_version = "6.2.1"

    const val recyclerview_version = "1.2.1"
    const val recyclerview_selection_version = "1.1.0"
    const val swiperefreshlayout_version = "1.1.0"
    const val lifecycle_viewmodel_ktx_version = "2.2.0"
    const val lifecycle_runtime_ktx_version = "2.2.0"
    const val lifecycle_livedata_ktx_version = "2.2.0"

    //external
    const val circleimageview_version = "3.1.0"

    //firebase
    const val firebase_bom_version = "28.2.0"
    const val firebase_auth_version = "21.0.1"
    const val google_play_service_auth_version = "19.0.0"
    const val photoview_version = "2.3.0"
    const val billing_version = "4.0.0"

    const val send_bird_version = "3.0.166"

    //Data store
    const val data_store = "1.0.0"

    const val lottie_version = "3.4.2"
}

object Deps{
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle_version}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_stdlib_version}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlib_version}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout_version}"

    //Testing
    const val junit = "junit:junit:${Versions.junit_version}"
    const val junit_test = "androidx.test.ext:junit:${Versions.junit_test_version}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core_version}"
    const val google_truth = "com.google.truth:truth:1.0.1"
    const val mockito_core = "com.mockito:mockito-core:2.21.0"

    //External
    const val facebook_android_sdk = "com.facebook.android:facebook-android-sdk:${Versions.facebook_android_sdk_version}"
    const val play_services_auth = "com.google.android.gms:play-services-auth:${Versions.play_services_auth_version}"
    const val gson = "com.google.code.gson:gson:${Versions.gson_version}"
    const val rx_java = "io.reactivex.rxjava3:rxjava:${Versions.rx_java_version}"
    const val rx_android = "io.reactivex.rxjava3:rxandroid:${Versions.rx_android_version}"
    const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android_versions}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi_version}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_version}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
    const val kodein_di_framework_androidx = "org.kodein.di:kodein-di-framework-android-x:${Versions.kodein_di_version}"
    const val kodein_di_generic_jvm = "org.kodein.di:kodein-di-generic-jvm:${Versions.kodein_di_version}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview_version}"
    const val recyclerview_selection = "androidx.recyclerview:recyclerview-selection:${Versions.recyclerview_selection_version}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout_version}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_viewmodel_ktx_version}"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime_ktx_version}"
    const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_livedata_ktx_version}"
    const val circle_imageview = "de.hdodenhof:circleimageview:${Versions.circleimageview_version}"

    //firebase
    const val firebase_messaging_ktx = "com.google.firebase:firebase-messaging-ktx"
    const val firebase_analytics_ktx = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom_version}"
    const val firebase_auth = "com.google.firebase:firebase-auth:${Versions.firebase_auth_version}"
    const val google_play_service_auth = "com.google.android.gms:play-services-auth:${Versions.google_play_service_auth_version}"
    const val photoview = "com.github.chrisbanes:PhotoView:${Versions.photoview_version}"
    const val google_billing = "com.android.billingclient:billing-ktx:${Versions.billing_version}"

    const val send_bird = "com.sendbird.sdk:sendbird-android-sdk:${Versions.send_bird_version}"

    //data store
    const val data_store = "androidx.datastore:datastore:${Versions.data_store}"
    const val data_store_core = "androidx.datastore:datastore-core:${Versions.data_store}"
    const val data_store_prefs = "androidx.datastore:datastore-preferences:${Versions.data_store}"
    const val data_store_prefs_core = "androidx.datastore:datastore-preferences-core:${Versions.data_store}"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie_version}"
}
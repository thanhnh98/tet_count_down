#include <jni.h>

const char* key_ads_interstitial = "ca-app-pub-3395045728500314/5395297950";
const char* key_ads_banner_about = "ca-app-pub-3395045728500314/8400351777";
const char* key_ads_banner_music = "ca-app-pub-3395045728500314/4091538690";

JNIEXPORT jstring JNICALL
Java_com_thanh_1nguyen_test_1count_1down_external_KeyStore_getAdsInterstitial(JNIEnv *env,jobject thiz) {
 return (*env)->  NewStringUTF(env, key_ads_interstitial);
}

JNIEXPORT jstring JNICALL
Java_com_thanh_1nguyen_test_1count_1down_external_KeyStore_getAdsBannerAbout(JNIEnv *env,
                                                                             jobject thiz) {
 return (*env)->  NewStringUTF(env, key_ads_banner_about);
}

JNIEXPORT jstring JNICALL
Java_com_thanh_1nguyen_test_1count_1down_external_KeyStore_getAdsBannerMusic(JNIEnv *env,
                                                                             jobject thiz) {
 return (*env)->  NewStringUTF(env, key_ads_banner_music);
}
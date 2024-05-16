#include <jni.h>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_rasyidin_connectopia_utils_Constants_webClientFirebaseAPI(JNIEnv *env, jobject thiz) {
    return env ->NewStringUTF("935113078481-bgpr1003em1skh3apn2puph488g8ses5.apps.googleusercontent.com");
}
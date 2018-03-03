#include <jni.h>
#include <string>
extern "C" {
#include "detect0131/detect0131.h"
}

#include <android/log.h>

#define  LOG    "native-lib"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__)
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__)


extern "C"
JNIEXPORT void JNICALL
Java_com_lzhz_lxh_sleepmonitor_tools_jni_JNIUtils_detect0131(JNIEnv *env, jclass type, jdoubleArray raw_data_,
                                                jdoubleArray hbrRes_, jdoubleArray HD_,
                                                jdoubleArray RD_, jdoubleArray snore_) {
    jdouble *raw_data = env->GetDoubleArrayElements(raw_data_, NULL);
    jdouble *hbrRes = env->GetDoubleArrayElements(hbrRes_, NULL);
    jdouble *HD = env->GetDoubleArrayElements(HD_, NULL);
    jdouble *RD = env->GetDoubleArrayElements(RD_, NULL);
    jdouble *snore = env->GetDoubleArrayElements(snore_, NULL);

    double HBR;
    double RES_R;

    detect0131(raw_data, &HBR, &RES_R, HD, RD, snore);
    LOGD("HBR = %f,RES_R = %f",HBR, RES_R);
//    memcpy(&hbrRes[0],&HBR, sizeof(HBR));
//    memcpy(&hbrRes[1],&RES_R, sizeof(RES_R));
    hbrRes[0] = HBR;
    hbrRes[1] = RES_R;

    env->ReleaseDoubleArrayElements(raw_data_, raw_data, 0);
    env->ReleaseDoubleArrayElements(hbrRes_, hbrRes, 0);
    env->ReleaseDoubleArrayElements(HD_, HD, 0);
    env->ReleaseDoubleArrayElements(RD_, RD, 0);
    env->ReleaseDoubleArrayElements(snore_, snore, 0);
}
/*extern "C"
JNIEXPORT void JNICALL

Java_com_lzhz_lxh_sleepmonitor_tools_jni_JNIUtils_detect0131(JNIEnv *env, jclass type,
                                                             jdoubleArray raw_data_,
                                                             jdoubleArray hbrRes_, jdoubleArray HD_,
                                                             jdoubleArray RD_,
                                                             jdoubleArray snore_) {
    jdouble *raw_data = env->GetDoubleArrayElements(raw_data_, NULL);
    jdouble *hbrRes = env->GetDoubleArrayElements(hbrRes_, NULL);
    jdouble *HD = env->GetDoubleArrayElements(HD_, NULL);
    jdouble *RD = env->GetDoubleArrayElements(RD_, NULL);
    jdouble *snore = env->GetDoubleArrayElements(snore_, NULL);

    // TODO

    env->ReleaseDoubleArrayElements(raw_data_, raw_data, 0);
    env->ReleaseDoubleArrayElements(hbrRes_, hbrRes, 0);
    env->ReleaseDoubleArrayElements(HD_, HD, 0);
    env->ReleaseDoubleArrayElements(RD_, RD, 0);
    env->ReleaseDoubleArrayElements(snore_, snore, 0);

}*/

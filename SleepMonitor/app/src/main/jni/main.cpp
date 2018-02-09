extern "C"
#include "com_lzhz_lxh_sleepmonitor_tools_jni_JNIUtils.h"
JNIEXPORT jstring JNICALL Java_com_lzhz_lxh_sleepmonitor_tools_jni_JNIUtils_detect0131
    (JNIEnv * env, jobject obj){
//如果是用C语言格式就用这种方式
//  return (*env)->NewStringUTF(env,"Kiss dream");
  //如果是用C语言格式就用这种方式
return env->NewStringUTF((char *)"Kiss dream");
}
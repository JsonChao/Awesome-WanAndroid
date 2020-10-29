#include <jni.h>
#include <string>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_pingan_bank_cpractice_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

// Copyright (C) 2010, Guy Barrand. All rights reserved.
// See the file exlib.license for terms.

#include <android/log.h>

#include "../../../../exlib/Android/log_streambuf"

#define EXLIB_GL_GLES

#include "../../../../exlib/exa/sg_cube"

#include <jni.h>

extern "C" {

jlong Java_fr_in2p3_lal_exlib_sg_Exa_nativeCreateExaObj(JNIEnv* a_env,jclass a_class,jstring a_doc_dir,jstring a_tmp_dir){

  // who delete the two below objects ?
  exlib::Android::log_streambuf* buf = new exlib::Android::log_streambuf("exlib_sg");
  std::ostream* cout = new std::ostream(buf);

  exlib::examples::sg::cube* exa = new exlib::examples::sg::cube(*cout);

  return (jlong)exa;
}

void Java_fr_in2p3_lal_exlib_sg_Exa_nativeDeleteExaObj(JNIEnv*,jobject a_object,jlong a_exa){
  exlib::examples::sg::cube* exa = (exlib::examples::sg::cube*)a_exa;
  delete exa;
}

jboolean Java_fr_in2p3_lal_exlib_sg_Exa_nativeTouchEventDown(JNIEnv* a_env,jobject a_object,jlong a_exa,jfloat a_x,jfloat a_y){

  //__android_log_print(ANDROID_LOG_INFO,
  // "Java_fr_in2p3_lal_exlib_sg_Exa_nativeTouchEventDown",
  // "event x = %g y = %g", a_x, a_y);

  exlib::examples::sg::cube* exa = (exlib::examples::sg::cube*)a_exa;

  unsigned int wh = exa->window_height();
  exa->mouse_down((unsigned int)a_x,(unsigned int)(wh-a_y));

  return true; //ask java to redisplay the view.
}

void Java_fr_in2p3_lal_exlib_sg_Exa_nativeSurfaceCreated(JNIEnv*,jobject a_object,jlong a_exa){
}

void Java_fr_in2p3_lal_exlib_sg_Exa_nativeSurfaceChanged(JNIEnv*,jobject a_object,jlong a_exa,jint w, jint h){
  exlib::examples::sg::cube* exa = (exlib::examples::sg::cube*)a_exa;
  exa->resize_window(w,h);
  //__android_log_print(ANDROID_LOG_INFO,
  //   "Java_fr_in2p3_lal_exlib_sg_Exa_nativeSurfaceChanged",
  //   "resize w=%d h=%d", w, h);
}

void Java_fr_in2p3_lal_exlib_sg_Exa_nativeDrawFrame(JNIEnv* a_env,jobject a_object,jlong a_exa){
  //__android_log_print(ANDROID_LOG_INFO,
  //		        "Java_fr_in2p3_lal_exlib_sg_Exa_nativeDrawFrame",
  //                    "begin");
  exlib::examples::sg::cube* exa = (exlib::examples::sg::cube*)a_exa;
  exa->render();
}

}

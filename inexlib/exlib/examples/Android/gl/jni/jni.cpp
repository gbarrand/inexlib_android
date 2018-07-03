// Copyright (C) 2010, Guy Barrand. All rights reserved.
// See the file exlib.license for terms.

#include <android/log.h>

#define EXLIB_GL_GLES
#include <exlib/GL/test>

#include <jni.h>

//NOTE : in a "true" application, the below static
//       would be handled with an object.
static int sWindowWidth  = 320;
static int sWindowHeight = 480;

extern "C" {

void Java_fr_in2p3_lal_exlib_gl_DemoRenderer_nativeSurfaceCreated(JNIEnv*){
}

void Java_fr_in2p3_lal_exlib_gl_DemoRenderer_nativeSurfaceChanged(JNIEnv*,jobject,jint w, jint h){
  sWindowWidth  = w;
  sWindowHeight = h;
  __android_log_print(ANDROID_LOG_INFO, "exlib/gl", "resize w=%d h=%d", w, h);
}

//void Java_fr_in2p3_lal_exlib_gl_DemoRenderer_nativeDone(JNIEnv*){
//  appClose();
//}

void Java_fr_in2p3_lal_exlib_gl_DemoGLSurfaceView_nativeTouchEventDown(JNIEnv*){
}

void Java_fr_in2p3_lal_exlib_gl_DemoRenderer_nativeDrawFrame(JNIEnv*){
  exlib::GL::test::do_gl(sWindowWidth,sWindowHeight);
}

}

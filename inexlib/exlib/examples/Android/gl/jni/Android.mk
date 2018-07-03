LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := exlib_gl

LOCAL_CFLAGS := -I../../..

LOCAL_SRC_FILES := jni.cpp

LOCAL_LDLIBS := -lGLESv1_CM -ldl -llog

include $(BUILD_SHARED_LIBRARY)

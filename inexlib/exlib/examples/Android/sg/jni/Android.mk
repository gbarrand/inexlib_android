LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := exlib_sg

LOCAL_CFLAGS := \
 -I../../..\
 -I../../../../inlib

LOCAL_SRC_FILES := jni.cpp

LOCAL_LDLIBS := -lGLESv1_CM -ldl -llog -lz

include $(BUILD_SHARED_LIBRARY)

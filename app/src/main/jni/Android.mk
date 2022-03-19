LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := secret_keys
LOCAL_SRC_FILES := secret_keys.c
include $(BUILD_SHARED_LIBRARY)
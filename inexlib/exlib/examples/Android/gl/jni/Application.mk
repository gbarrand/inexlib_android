
#APP_ABI := all
#APP_ABI := armeabi                           # ndk-r7, ndk-r10e
#APP_ABI := arm64-v8a armeabi-v7a x86 x86_64  # ndk-r17
#APP_ABI := arm64-v8a                         # Galaxy Note 8.
#APP_ABI := armeabi-v7a                       # Nexus-10

#APP_STL := gnustl_static
#APP_PLATFORM := android-9

APP_STL := c++_static       # with ndk-r17.
APP_PLATFORM := android-14  # android-ndk-r15b compells that. Android >= 4.0.1 (Ice Cream Sandwich).



set sdk_home=/usr/local/Android/android-sdk_r16-macosx

set ndk_home=/usr/local/Android/android-ndk-r10e

setenv PATH "${sdk_home}/tools:${PATH}"
setenv PATH "${sdk_home}/platform-tools:${PATH}"
setenv PATH "${ndk_home}:${PATH}"

if ( "`uname`" == "Darwin" ) then
  setenv PATH "${PATH}:/opt/local/bin"   # for ant.
endif

unset sdk_home
unset ndk_home

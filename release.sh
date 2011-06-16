#! /bin/sh -e
####
# Build a new release
#
# - Modify build.conf as needed
# - Usage: ./build_release.sh <version>

if [ ! -f build.conf ]; then
  echo "Config file missing: build.conf"
  echo "Modify the build.conf.sample file and save it as build.conf"
  exit 1
fi

# dash seems to need "./" here
. ./build.conf
BUILD_DIRECTORY="${BUILD_DIRECTORY:-bin}"

if [ -z "$1" ] ; then
  echo "Release version is missing."
  echo "Usage: ./build_release.sh <version>"
  exit 1
fi

if [ -z "$ANDROID_SDK" ] ; then
  echo "Can not build without knowing where to find the Android SDK!"
  exit 1
fi

echo "Building yaaic $1"
echo ""
cd application
ant ${2:-release}
cd ..
jarsigner -verbose -keystore "${KEYSTORE:-~/.keystore}" "$BUILD_DIRECTORY/Yaaic-unsigned.apk" "${RELEASE_KEY:-release}"
jarsigner -verify "$BUILD_DIRECTORY/Yaaic-unsigned.apk"
"$ANDROID_SDK/tools/zipalign" -v 4 "$BUILD_DIRECTORY/Yaaic-unsigned.apk" "$BUILD_DIRECTORY/yaaic-$1.apk"
echo ""
echo "Build ready: $BUILD_DIRECTORY/yaaic-$1.apk"

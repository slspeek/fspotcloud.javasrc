mkdir -p target/classes
(echo "Version: $1 \nBuild tag: $BUILD_TAG\n"; hg tip ; hg status) > build/classes/main/version.txt

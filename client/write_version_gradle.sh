mkdir -p target/classes
(echo Version: $1 $BUILD_TAG; hg id ; hg status) > build/classes/main/version.txt

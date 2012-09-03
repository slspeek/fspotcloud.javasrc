mkdir -p target/classes
(echo Version: $1; hg tip ; hg status) > build/classes/main/version.txt

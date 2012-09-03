mkdir -p target/classes
(echo Version: $1; hg tip ; hg status) > target/classes/version.txt

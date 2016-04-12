pkgver() {
		  printf "%s.%s" "$(git rev-list --count HEAD)" "$(git rev-parse --short HEAD)"
}
BUILD=$(pkgver)
mkdir -p target/classes
(echo "\nVersion: $1 \nBuild tag: $BUILD\n"; git log -1) > build/classes/main/version.txt

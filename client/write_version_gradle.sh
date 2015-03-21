pkgver() {
		  printf "%s.%s" "$(git rev-list --count HEAD)" "$(git rev-parse --short HEAD)"
}
BUILD=$(pkgver)
mkdir -p target/classes
(echo -e "Version: $1 \nBuild tag: $BUILD\n") > build/classes/main/version.txt

#!/bin/bash
APPCFG="$INSTALL_PATH/appengine-java-sdk-${gae.version}/bin/appcfg.sh"
$APPCFG --application=$application.id --version=1 --email=$email --enable_jar_splitting rollback $INSTALL_PATH/war
echo Press enter to close this window
read
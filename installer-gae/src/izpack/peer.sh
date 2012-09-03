#!/bin/bash
java -Dpause=30 \
     -Djava.util.logging.config.file=logging.properties \
     -Dendpoint=${application.id}.appspot.com \
     -Dbot.secret=$bot.secret.userinput \
     -Ddb=$photos.db.userinput  \
     -cp $INSTALL_PATH/peer-jar-with-dependencies.jar \
     com.googlecode.fspotcloud.peer.Main

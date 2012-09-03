#!/bin/bash
PROJECT_VERSION=$1
ICON_SIZE=$2
for ORIG in target/classes/images/originals/*.png;
do 
  IMAGE_BASE=$(basename $ORIG)
  IMAGE_PATH=$(dirname $ORIG)
  convert -geometry $ICON_SIZE $IMAGE_PATH/$IMAGE_BASE target/classes/images/$IMAGE_BASE 
done



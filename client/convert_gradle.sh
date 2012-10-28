#!/bin/bash
ICON_SIZE=$1
mkdir -p build/classes/main/images
for ORIG in build/resources/main/images/originals/*.png;
do 
  IMAGE_BASE=$(basename $ORIG)
  IMAGE_PATH=$(dirname $ORIG)
  convert -geometry $ICON_SIZE $IMAGE_PATH/$IMAGE_BASE build/classes/main/images/$IMAGE_BASE 
done



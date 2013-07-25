#!/bin/bash
SIZE=60
for ORIG in $(cat src/main/resources/icons-from-iconset.lst)
do 
    icons --icon $ORIG\
          --output src/main/resources/images/$ORIG.png\
          --width $SIZE\
          --height $SIZE\
          --shadow \
          --maincolor FFFFFF\
          --linecolor FFFFFF\
          --bgcolor 000000
done
icons --icon mail\
      --output src/main/resources/images/mail.png\
      --width $SIZE\
      --height $SIZE\
      --shadow \
      --maincolor FFFFFF\
      --linecolor C0C0C0\
      --bgcolor 000000


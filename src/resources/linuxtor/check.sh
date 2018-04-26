#!/bin/sh
xdg-open https://github.com/BitsByWill/WVG/releases >/dev/null
if [ $? -ne 0 ]
then
	echo -e "Update page unable to be opened.  This might be due to an \nissue with your browser"
fi
exit
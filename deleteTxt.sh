for f in ./WebPages/Text/*;do
	fileName=$(echo "${f::-4}"|cut -d "/" -f4)
	if [ ! -e "./WebPages/$fileName.html" ]; then
		rm -f "$f"
	fi
done

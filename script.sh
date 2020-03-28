for f in ./WebPages/Text/*;do
	if [ $(wc -w "$f"|cut -d " " -f1) -gt 7999 ];then
		fileName=$(echo "${f::-4}"|cut -d "/" -f4)
		if [ -e "./WebPages/$fileName.html" ]; then
			rm -f "./WebPages/$fileName.html"
			rm -f "$f"
			echo "Removed ./WebPages/$fileName.html"
		fi
	fi
done

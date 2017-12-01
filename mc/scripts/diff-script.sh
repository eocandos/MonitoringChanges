#!/bin/bash
# This script take the two files generate for 'downloader-script' and make a difference between both and create a version 3 of the file

	NUMBER=1;

#	=================================================  #
#	=================== FUNCTIONS ===================  #
	joinFiles(){		

		for fileName in $PWD/temp/*; do
			NAME=${fileName##*/} 
			# createTempFile $NAME
			if [ $NUMBER -eq 1 ]
			then
				FILE1=$fileName					
				NUMBER=2				
			else
				FILE2=$fileName
				NUMBER=1	
				# echo "=FILE1" $FILE1 "FILE2" $FILE2
				diff -a --suppress-common-lines $FILE1 $FILE2 > $PWD/diff/$NAME'(diff)'	 	
			fi		 
		done
	}

	deleteEmptyFile(){

		for fileName in $PWD/diff/*; do
			if [ -s $fileName ] 
			then
				echo "$fileName Ok"
			else
				echo "$fileName deleted"
				rm $fileName		  
			fi
		done
	}

	createTempFile(){
		cat <<EOF > $PWD/diff.log
		"Path: " $PWD
		"Value" $1
EOF
	}

#	=================================================  #
#	=================== JOIN FILES===================  #
	# ==== Diff collections
	cd $PWD/exports/
	# echo $PWD
	joinFiles
	# ==== Deleting empty files
	deleteEmptyFile
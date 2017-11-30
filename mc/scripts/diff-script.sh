#!/bin/bash
# This script take the two files generate for 'downloader-script' and make a difference between both and create a version 3 of the file

	NUMBER=1;

#	=================================================  #
#	=================== FUNCTIONS ===================  #
	joinFiles(){		

		for fileName in temp/*; do
			NAME=${fileName##*/} 
			if [ $NUMBER -eq 1 ]
			then
				FILE1=$fileName	
				NUMBER=2				
			else
				FILE2=$fileName
				NUMBER=1	
				diff -a --suppress-common-lines $FILE1 $FILE2 > $PWD/final/$NAME'(diff)'	 	
			fi		 
		done
	}

	deleteEmptyFile(){

		for fileName in $PWD/final/*; do
			if [ -s $fileName ] 
			then
				echo "$fileName Ok"
			else
				echo "$fileName deleted"
				rm $fileName		  
			fi
		done
	}

#	=================================================  #
#	=================== JOIN FILES===================  #
	# ==== Diff collections
	cd ../exports/
	# echo $PWD
	joinFiles
	# ==== Deleting empty files
	deleteEmptyFile
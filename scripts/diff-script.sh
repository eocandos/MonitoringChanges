#!/bin/bash
# This script take the two files generate for 'downloader-script' and make a difference between both and create a version 3 of the file

	NUMBER=1;
	PROJECT_PATH=/home/eocandos/MonitorerChanges;
	COLLECTIONS_PATH=/home/eocandos/MonitorerChanges/mc/exports/collections/SWD_DB;
	LOGS_PATH=/home/eocandos/MonitorerChanges/mc/exports/logs;	
	RESULT_PATH=/home/eocandos/MonitorerChanges/mc/exports/results

	joinFiles(){

		cd $1/original/
		for fileName in $1/original/*; do
			NAME=${fileName##*/} 
			if [ $NUMBER -eq 1 ]
			then
				FILE1=$NAME	
				NUMBER=2
			else
				FILE2=$NAME
				NUMBER=1
				# sdiff $FILE1 $FILE2 > $RESULT_PATH/$FILE1'(diff)'
				# diff -y -a $FILE1 $FILE2 > $RESULT_PATH/$FILE1'(diff)'	 		
				diff -a --suppress-common-lines $FILE1 $FILE2 > $RESULT_PATH/$FILE1'(diff)'	 	
			fi		 
		done
	}

	deleteEmptyFile(){

		for fileName in $RESULT_PATH/*; do

			if [ -s $fileName ] 
			then
				echo "$fileName Ok"
			else
				echo "$fileName deleted"
				rm $fileName		  
			fi
		done
	}

	# ==== Diff collections
	joinFiles $COLLECTIONS_PATH
	# ==== Diff logs
	joinFiles $LOGS_PATH
	# ==== Deleting empty files
	deleteEmptyFile
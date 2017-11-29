#!/bin/bash

RESULT_PATH=/home/eocandos/MonitorerChanges/mc/exports/results/

# LOCAL_DB_PATH=/home/eocandos/MonitorerChanges/mc/exports/collections/SWD_DB/
# cd $PATH_PROJECT;
# touch testFileGenerated.txt;

# echo 'changed!'
# sed -i 's/NUMBER=1/NUMBER=2/g' downloader-script.sh

# for file in $LOCAL_DB_PATH*
# do
#     echo $file
# done


# if [ $fileNum -eq 1 ]
# then
# 	FILE1=$fileName	
# 	fileNum=2
# 	echo "One"
# else
# 	FILE2=$fileName	
# 	fileNum=1
# 	joinFiles $FILE1 $FILE2
# 	echo "two"
# if		

	# fileName=1
	# if [ $fileName -eq 1 ]
	# then
	# 	FILE1=$fileName	
	# 	fileNum=2
	# 	echo "One"
	#  else
	#  	FILE2=$fileName	
	# 	fileNum=1
	# 	# joinFiles $FILE1 $FILE2	 		
	# 	echo "One"		
	# fi


deleteEmptyFile(){

	for fileName in $RESULT_PATH*; do

		if [ -s $fileName ] 
		then
			echo "$fileName Ok"
		else
			echo "$fileName deleted"
			rm $fileName		  
		fi
	done
}

deleteEmptyFile 
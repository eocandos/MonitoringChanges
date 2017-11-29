#!/bin/bash
# This script generate the exports of databases, console outputs, logs, etc


#	=================================================  #
#	=================== VARIABLES ===================  #

NUMBER=1;
SCRIPTS_PATH='/home/eocandos/MonitorerChanges/mc/scripts';
LOGS_LINES_AMOUNT=50;
PROJECT_PATH='/home/eocandos/MonitorerChanges/';
COLLECTIONS_PATH=/home/eocandos/MonitorerChanges/mc/exports/collections/SWD1-4/original;
LOGS_PATH=/home/eocandos/MonitorerChanges/mc/exports/logs/original;	
LOGS_ORIGINAL_ECLIPSE_PATH=/home/eocandos/Develop/Certigestor/logs-eclipse/;                 

#	=================================================  #
#	================ EXPORT FILES ===================  #

# ======= Get collection names ====== #
collections=`echo "show collections" | mongo SWD1-4 --quiet`

for collection in $collections;
do 
	# Export all collections 
	echo "..:: Exporting collection $collection ::..";
	mongoexport --db SWD1-4 --collection $collection --out $COLLECTIONS_PATH/$collection$NUMBER.json --pretty
done

# ========= Get logs ===========
# cd $LOGS_ORIGINAL_ECLIPSE_PATH

# for fileName in $LOGS_ORIGINAL_ECLIPSE_PATH*; do
# 	NAME=${fileName##*/}
# 	echo $NAME
# 	tail -n $LOGS_LINES_AMOUNT $NAME >> $LOGS_PATH/$NAME$NUMBER.log
# done

cd $PROJECT_PATH/mc/scripts
echo "[Ok] Copied logs!";	

# ========= Change Number ==========
	if [ $NUMBER -eq 1 ]			
then
	sh setNumberTwoScript.sh
else
	sh setNumberOneScript.sh
fi

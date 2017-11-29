#!/bin/bash
# This script generate the exports of databases, console outputs, logs, etc
# Two versions of file fe: auth-manager1.log auth-manager2.log

#	=====================================================================  #
#	=========================== VARIABLES ===============================  #

NUMBER=1;
DB_NAME=SWD_DB:
SCRIPTS_PATH='/home/eocandos/MonitorerChanges/mc/scripts';
LOGS_LINES_AMOUNT=50;
PROJECT_PATH='/home/eocandos/MonitorerChanges/';
COLLECTIONS_PATH=/home/eocandos/MonitorerChanges/mc/exports/collections/SWD_DB/original;
LOGS_PATH=/home/eocandos/MonitorerChanges/mc/exports/logs/original;	
LOGS_ORIGINAL_ECLIPSE_PATH=/home/eocandos/Develop/Certigestor/logs-eclipse/;                 

#	=====================================================================  #
#	========================= EXPORT FILES ==============================  #

# ======= Get collection names ====== #
collections=`echo "show collections" | mongo SWD_DB --quiet`

for collection in $collections;
do 
	# Export all collections 
	echo "..:: Exporting collection $collection ::..";
	mongoexport --db SWD_DB --collection $collection --out $COLLECTIONS_PATH/$collection$NUMBER.json --pretty
done

# ========= Get logs ===========
cd $LOGS_ORIGINAL_ECLIPSE_PATH

for fileName in $LOGS_ORIGINAL_ECLIPSE_PATH*; do
	NAME=${fileName##*/}
	echo $NAME
	tail -n $LOGS_LINES_AMOUNT $NAME >> $LOGS_PATH/$NAME$NUMBER.log
done

cd $PROJECT_PATH/mc/scripts
echo "[Ok] Copied logs!";	

# ========= Change Number ==========
	if [ $NUMBER -eq 1 ]			
then
	sh setNumberTwoScript.sh
else
	sh setNumberOneScript.sh
fi

#!/bin/bash
# This script generate the exports of databases, console outputs, logs, etc

#	=================================================  #
#	=================== VARIABLES ===================  #

NUMBER=1;
NAME_DB='SWD_DB'
LOGS_LINES_AMOUNT=25;
LOGS_ORIGINAL_ECLIPSE_PATH=/home/eocandos/Develop/Certigestor/logs-eclipse/;
       
#	=================================================  #
#	================ EXPORT FILES ===================  #

# ======= Get json mongo collection ====== #
cd ..
TEMP_FOLDER=$PWD/exports/temp
collections=`echo "show collections" | mongo $NAME_DB --quiet`

for collection in $collections;
do 
	# Export all collections 
	echo "..:: Exporting collection $collection ::..";
	mongoexport --db $NAME_DB --collection $collection --out $TEMP_FOLDER/$collection$NUMBER.json --pretty

done
echo "== [Ok] Copied Collections! ==";

# ============= Get console logs ============= #
cd $LOGS_ORIGINAL_ECLIPSE_PATH

for fileName in $LOGS_ORIGINAL_ECLIPSE_PATH*; do
	NAME=${fileName##*/}
	echo $NAME
	tail -n $LOGS_LINES_AMOUNT $NAME >> $TEMP_FOLDER/$NAME$NUMBER.log
done
echo "== [Ok] Copied logs! ==";	

# ========= Change Number ========== #
cd $TEMP_FOLDER
cd ../../scripts

	if [ $NUMBER -eq 1 ]			
then
	sh setNumberTwoScript.sh
else
	sh setNumberOneScript.sh
fi

#!/bin/bash

	# Short line number on console log
	# Develop CS Script 
	# Script version 4.0
	# Tasks:
	#   - diff on two logs and to save this difference
	#		< diff -a --suppress-common-lines -y file1.log file2.log > file3.log >
	#   - Create a new collection on mongo and to save the json saved on git

#	=====================================================================  #
#	=========================== VARIABLES ===============================  #

DB_NAME='SWD_DB':
LOGS_LINES_AMOUNT=50;
PATH_PROJECT='/home/eocandos/Develop/MonitorerChangesProjects';


#	=====================================================================  #
#	=========================== FUNCTIONS ===============================  #

#===== Clean logs fuction =====
cleanLogs(){

	> $PATH_PROJECT/logs/auditor-manager.log 
	> $PATH_PROJECT/logs/auth-manager.log
	> $PATH_PROJECT/logs/content-manager.log 
	> $PATH_PROJECT/logs/business-report.log 
	> $PATH_PROJECT/logs/customer-settings.log 
	> $PATH_PROJECT/logs/document-manager.log 
	> $PATH_PROJECT/logs/mensage-enqueuer.log 
	> $PATH_PROJECT/logs/mensage-reinjector.log 
	> $PATH_PROJECT/logs/portal-functionary.log 
	> $PATH_PROJECT/logs/record-manager.log 
	> $PATH_PROJECT/logs/single-window.log 
	> $PATH_PROJECT/logs/virtual-office.log 
	> $PATH_PROJECT/logs/workflow-manager.log	
	echo "[Ok] Cleaned logs!";
}

#===== To segment log and to generate a copy function ======
copyLogs(){

	cd /home/eocandos/Develop/Certigestor/logs-eclipse/
	tail -n $LOGS_LINES_AMOUNT auditor-manager.log >> $PATH_PROJECT/logs/auditor-manager.log 
	tail -n $LOGS_LINES_AMOUNT auth-manager.log >> $PATH_PROJECT/logs/auth-manager.log
	tail -n $LOGS_LINES_AMOUNT content-manager.log >> $PATH_PROJECT/logs/content-manager.log
	tail -n $LOGS_LINES_AMOUNT business-report.log >> $PATH_PROJECT/logs/business-report.log 
	tail -n $LOGS_LINES_AMOUNT customer-settings.log >> $PATH_PROJECT/logs/customer-settings 
	tail -n $LOGS_LINES_AMOUNT document-manager.log >> $PATH_PROJECT/logs/document-manager.log 
	tail -n $LOGS_LINES_AMOUNT mensage-enqueuer.log >> $PATH_PROJECT/logs/mensage-enqueuer.log 
	tail -n $LOGS_LINES_AMOUNT mensage-reinjector.log >> $PATH_PROJECT/logs/mensage-reinjector.log 
	tail -n $LOGS_LINES_AMOUNT portal-functionary.log >> $PATH_PROJECT/logs/portal-functionary.log 
	tail -n $LOGS_LINES_AMOUNT record-manager.log >> $PATH_PROJECT/logs/record-manager.log 
	tail -n $LOGS_LINES_AMOUNT single-window.log >> $PATH_PROJECT/logs/single-window.log 
	tail -n $LOGS_LINES_AMOUNT virtual-office.log >> $PATH_PROJECT/logs/virtual-office.log 
	tail -n $LOGS_LINES_AMOUNT workflow-manager.log	>> $PATH_PROJECT/logs/workflow-manager.log
	cd	
	cd $PATH_PROJECT
	echo "[Ok] Copied logs!";	
}

#===== Pull comiits to git fuction =====
pullChanges(){

	# ======= Get collection names ====== #
	echo "$1 $2 $3 $4 $5 $6 $7 $8 $9 $10 $11 $12 $13 $14 $15"
	collections=`echo "show collections" | mongo SWD_DB --quiet`

	for collection in $collections;
	do 
		# Export all collections 
		echo "..:: Exporting collection $collection ::..";
		mongoexport --db SWD_DB --collection $collection --out /home/eocandos/Develop/MonitorerChangesProjects/mongoBDs/SWD_DB/$collection.json --pretty
	done
	
	cd 
	cd $PATH_PROJECT

	# ======= Git push ======= #
	echo "..:: Updating git ::..";
	git add *
	git commit -m "$1 $2 $3 $4 $5 $6 $7 $8 $9 $10 $11 $12 $13 $14 $15"
	git push	
}

#	=====================================================================  #
#	=========================== MAIN FLOW ===============================  #

	# ==== Get input and choose process ===== #
	echo "Choose [ Enter:Comiit - 0:EraseLogs ] >";
	read step;

	if [ $step -eq 0 ]
	then

		echo "¿Are you sure to clean the logs?";
		read rta;
		if [ $rta -eq 1 ]
			then
	 			cleanLogs
	 	else
	 		echo "[X] Canceled Erase!";
	 	fi		
	else

		cleanLogs
		copyLogs
		echo "Commit text: >";
		read commitStr;	
	 	pullChanges $commitStr

	fi
		




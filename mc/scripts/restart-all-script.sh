#!/bin/bash

#Savind summary process excecution
# cp exports/final-record.out /home/eocandos/Dropbox/[CertiCamara]/Certisolucion/records-mc-tool/

#Clean folders
rm -r exports/
mkdir -p exports/temp
mkdir -p exports/diff
cd exports/
touch diff-record.out

#Set Number 1 
cd ../../scripts/
sh setNumberOneScript.sh
#!/bin/bash

#Clean folders
cd exports/
rm -r temp/
rm -r diff/

mkdir temp/
mkdir diff/

#Set Number 1 
cd ../scripts/
sh setNumberOneScript.sh
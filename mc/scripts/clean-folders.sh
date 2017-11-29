#!/bin/bash

PROJECT_PATH='/home/eocandos/MonitorerChanges/mc/';

cd $PROJECT_PATH;
rm -r exports/
mkdir exports/
cd exports/
mkdir -p collections/SWD_DB/original/
mkdir -p collections/SWD1-4/original/
mkdir -p logs/original/
mkdir -p results/
mkdir -p final/
cd final/
touch final-record.out

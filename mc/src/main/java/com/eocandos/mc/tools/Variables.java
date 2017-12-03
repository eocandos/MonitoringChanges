package com.eocandos.mc.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Variables {

	public static final String FORMAT_DATE = "MM-dd-yyyy_HH-mm-ss";

	// Scripts
	public static final String DOWNLOADER_SCRIPT = "scripts/downloader-script.sh";
	public static final String DIFF_CALCULATOR_SCRIPT = "scripts/diff-script.sh";
	public static final String RESTART_ALL_SCRIPT = "scripts/restart-all-script.sh";

	// Folders
	public static final String FINAL_RECORD_FOLDER = "exports/final";
	public static final String DIFF_FOLDER = "exports/diff";
	
	// Messages
	public static final String INITIAL_MESSAGE = "** [1] for Record - Other for Finish: ";
	public static final String FINALL_MESSAGE = "** Record finalized correcly ***";
	public static final String PROCESS_NAME_MESSAGE = "Process Name: ";
	public static final String DESCRIPTION_MESSAGE = "Description: ";
	public static final String CHANGES_ON_FILE = "Changes on: ";
	public static final String OK_MESSAGE = "<Ok>";
	
	
	// Mapper Records
	public static final String CREATION_DATE_INDEX = "creationDate";
	public static final String PROCESS_NAME_INDEX = "processName";
	public static final String DESCRIPTION_INDEX = "description";
	public static final String SEPARATOR_INDEX = "separator";
	
	public static final String CREATION_DATE_VALUE = "DATE: ";
	public static final String PROCESS_NAME_VALUE = "PROCESS NAME:";
	public static final String DESCRIPTION_VALUE = "DESCRIPTION:";
	public static final String SEPARATOR_VALUE = "______________________________________________________________________________";
	

	
}

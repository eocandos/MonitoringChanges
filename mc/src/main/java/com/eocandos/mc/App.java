package com.eocandos.mc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{	
	//http://blog.eviltester.com/2010/05/a-selenium-capturenetworktraffic-example-in-java.html
	//https://stackoverflow.com/questions/13537533/automatize-har-files-generation-from-google-chrome
	//https://developer.chrome.com/extensions/webRequest
	//https://toolbox.googleapps.com/apps/har_analyzer/
	
    /** 
     * 
     * Create general model - Diagram class ..
     * 
     * 1. (Console)
     * - Create general system of to run the scripts and export and create of dbs and logs files
     * - Run script / bash (ok)
     * - Create two versions of the files { . Logs . jsons } (ok)
     * - Join all on a only file (Java) (ok)
     * - Debbug and depurate all (Ok)
     * - Better diff command ( Commit Git )
     * - Only a folder project files
     * - Script clean folders 
     * - Organice code
     * - Git scripts
     * - Set data from java to script { db, path, values, etc }
     * - Use yml
     * 
     * - Get console output logs info { Headers, Response }
     * - 
     * 
     * 2. (Back)
     * - Business + Persistence {  Some methods, save: name, desc, path .. on oracle db  }   
	**/
	
	public static String target = null;
	public static Runtime rt = Runtime.getRuntime();			
	public static Process proc = null;		
	
	public static String DOWNLOADER_SCRIPT_PATH = "/home/eocandos/MonitoringChanges/mc/scripts/downloader-script.sh";
	public static String DIFF_CALCULATOR_SCRIPT_PATH = "scripts/diff-script.sh";
	public static String CLEANER_SCRIPT_PATH = "scripts/clean-folders.sh";
	public static String FINAL_RECORD_FILE="exports/final/final-record.out";
	public static String RESULT_FOLDER = "exports/temp";
	
	public static String TEST_SCRIPT_PATH = "scripts/testCommands2.sh";
	
    public static void main( String[] args )
    {
    	
    	//runScript(TEST_SCRIPT_PATH, "StartingTest", "EndingTest");
    	
    	runScript(TEST_SCRIPT_PATH, "Start", "End");
    	
        /**
         * Generating a file with all changes of last process executed
         * */	
        //getChangesOfProcess();
		    	
    }

	public static void getChangesOfProcess() {
	
    	runScript(CLEANER_SCRIPT_PATH, "", "Cleaned Folders ::..");
    	runScript(DOWNLOADER_SCRIPT_PATH, "..:: Starting record ..", "");
    	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));			
		System.out.print("[1] To save record - [Other] Discard \n");			 
		String response;
		
		try {
			
			response = reader.readLine();
			if(response.equals("1")) {

				runScript(DOWNLOADER_SCRIPT_PATH, "..:: Ending record ..", "Record finalized successfully! ::..");				
				runScript(DIFF_CALCULATOR_SCRIPT_PATH, "..:: Getting changes ..", "Changes saved orrectly! ::..");
		    	final File folder = new File(RESULT_FOLDER);
		    	saveResults(folder);
		    	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void runScript(String path, String mssStart, String mssEnd) {

    	try {

			System.out.println(mssStart);
			target = new String(path);
			rt = Runtime.getRuntime();
			proc = rt.exec(target);
			proc.waitFor();
			System.out.println(mssEnd);
			System.out.println("Runned the script: "+path);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
    
	public static void saveResults(final File folder) {		
		
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {

				saveResults(fileEntry);

			} else {
				String nameFile = fileEntry.getName().substring(0, fileEntry.getName().length() - 11);
				System.out.println("Changes on file: " + nameFile);
				insertNewLineInFile(FINAL_RECORD_FILE,
						"\n ========== " + nameFile + " ========== \n");
				getFileContain(fileEntry.getPath());
			}
		}
	}
    
	private static void getFileContain(String path) {

		File file = new File(path);
		try {
			
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine() + "\n";
				insertNewLineInFile(FINAL_RECORD_FILE, line);
			}
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}

	}
    
    private static void insertNewLineInFile(String path, String line) {
    	
    	try {
    	    Files.write(Paths.get(path), line.getBytes(), StandardOpenOption.APPEND);
    	}catch (IOException e) {
    		e.getStackTrace();
    	}
    }
}



/**
 * Create Persistence layer and insert all in a table ( Psqul - Oracle)
 * **/











































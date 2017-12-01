package com.eocandos.mc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.eocandos.mc.tools.IException;

public class App {
	// http://blog.eviltester.com/2010/05/a-selenium-capturenetworktraffic-example-in-java.html
	// https://stackoverflow.com/questions/13537533/automatize-har-files-generation-from-google-chrome
	// https://developer.chrome.com/extensions/webRequest
	// https://toolbox.googleapps.com/apps/har_analyzer/

	/**
	 * 
	 * Create general model - Diagram class ..
	 * 
	 * 1. (Console) - Create general system of to run the scripts and export and
	 * create of dbs and logs files - Run script / bash (ok) - Create two
	 * versions of the files { . Logs . jsons } (ok) - Join all on a only file
	 * (Java) (ok) - Debbug and depurate all (Ok) - Better diff command ( Commit
	 * Git ) - Organice code - Git scripts - Set data from java to script { db,
	 * path, values, etc } - Use yml
	 * 
	 * - Get console output logs info { Headers, Response } -
	 * 
	 * 2. (Back) - Business + Persistence { Some methods, save: name, desc, path
	 * .. on oracle db }
	 **/
	
	/////////////////////////////////////
	////  		VARIABLES 			 ////
	/////////////////////////////////////

	public static String target = null;
	public static Runtime rt = Runtime.getRuntime();
	public static Process proc = null;

	// Variables
	public static String FILE_NAME = null;
	public static String FORMAT_DATE = "MM-dd-yyyy_HH-mm-ss";

	// Scripts
	public static String DOWNLOADER_SCRIPT = "scripts/downloader-script.sh";
	public static String DIFF_CALCULATOR_SCRIPT = "scripts/diff-script.sh";
	public static String RESTART_ALL_SCRIPT = "scripts/restart-all-script.sh";

	// Folders
	public static String FINAL_RECORD_FOLDER = "exports/final";
	public static String DIFF_FOLDER = "exports/diff";

	public static void main(String[] args) {

		/**
		 * Generating a file with all changes of last process executed
		 */
		getChangesOfProcess();

	}

	/////////////////////////////////////
	//// PUBLIC METHODS ////
	/////////////////////////////////////

	public static void getChangesOfProcess() {

		runScript(RESTART_ALL_SCRIPT);
		runScript(DOWNLOADER_SCRIPT);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("[1] To save record - [Other] Discard \n");
		String response;

		try {

			response = reader.readLine();
			if (response.equals("1")) {

				// Export and generate files
				runScript(DOWNLOADER_SCRIPT);

				// Get differences on the saved files
				runScript(DIFF_CALCULATOR_SCRIPT);

				// Create a new file
				Boolean createdEither = createNewFile(FINAL_RECORD_FOLDER, getNewName());

				// Save changes due to operation
				final File folder = new File(DIFF_FOLDER);
				saveDifferences(folder);

				// Restart all
				runScript(RESTART_ALL_SCRIPT);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void runScript(String pathScript) {

		try {

			target = new String(pathScript);
			rt = Runtime.getRuntime();
			proc = rt.exec(target);
			proc.waitFor();
			System.out.println("<Ok> Script: " + pathScript);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void saveDifferences(final File folderWithDiffs) {

		for (final File fileEntry : folderWithDiffs.listFiles()) {
			if (fileEntry.isDirectory()) {

				saveDifferences(fileEntry);

			} else {

				String nameFile = fileEntry.getName().substring(0, fileEntry.getName().length() - 11);

				System.out.println("Changes on file: " + nameFile);

				insertNewLineInFile(FINAL_RECORD_FOLDER, "\n \n========== " + nameFile + " ==========");

				getFileContain(fileEntry.getPath());
			}
		}
	}

	public static Boolean createNewFile(String path, String name) {

		Boolean response = null;
		String filePath = path + "/" + name;

		try {

			File file = new File(filePath);
			file.createNewFile();
			response = true;

		} catch (IOException e) {
			response = false;
			e.printStackTrace();
		}

		return response;

	}

	public static String getNewName() {

		FILE_NAME = "Record-" + getActualDate() + ".js";
		return FILE_NAME;

	}

	public static String getActualDate() {

		String date = null;
		try {

			Date myDate = new Date();
			SimpleDateFormat mdyFormat = new SimpleDateFormat(FORMAT_DATE);
			date = mdyFormat.format(myDate);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return date;

	}

	/////////////////////////////////////
	//// PRIVATE METHODS ////
	/////////////////////////////////////

	private static void getFileContain(String path) {

		File file = new File(path);
		try {

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = ("\n" + scanner.nextLine());
				insertNewLineInFile(FINAL_RECORD_FOLDER, line);
			}
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}

	}

	private static void insertNewLineInFile(String path, String line) {

		try {
			String pathSummaryFile = path + "/" + FILE_NAME;
			Files.write(Paths.get(pathSummaryFile), line.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}

/**
 * Create Persistence layer and insert all in a table ( Psqul - Oracle)
 **/

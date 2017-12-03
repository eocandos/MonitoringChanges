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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.eocandos.mc.tools.Variables;

public class App {

	// http://blog.eviltester.com/2010/05/a-selenium-capturenetworktraffic-example-in-java.html
	// https://stackoverflow.com/questions/13537533/automatize-har-files-generation-from-google-chrome
	// https://developer.chrome.com/extensions/webRequest
	// https://toolbox.googleapps.com/apps/har_analyzer/

	/**
	 * 
	 * 1.** Create general model - Diagram class ..
	 * 
	 * - Export HAR file { Headers, Response }
	 * - Better diff command ( Commit
	 * - That run jar -jar
	 * - Use yml
	 * 
	 * 2. (Back) - Business + Persistence { Some methods, save: name, desc, path
	 * .. on oracle db }
	 **/

	////////////////////////
	//// ATRIBUTES
	////////////////////////

	public static String target = null;
	public static Runtime rt = Runtime.getRuntime();
	public static Process proc = null;

	// Variables
	public static String FILE_NAME = null;
	// public static final String FORMAT_DATE = "MM-dd-yyyy_HH-mm-ss";
	public static final String ACTUAL_DATE = getActualDate();
	public static HashMap<String, String> recordInfoMap = new HashMap<String, String>();

	public static void main(String[] args) {

		/**
		 * Generating a file with all changes of last process executed
		 */
		getChangesOfProcess();

		/**
		 * Create Persistence layer and insert all in a table ( Psqul - Oracle)
		 **/

	}

	/////////////////////////////
	//// PUBLIC METHODS
	/////////////////////////////

	public static void getChangesOfProcess() {

		runScript(Variables.RESTART_ALL_SCRIPT);
		runScript(Variables.DOWNLOADER_SCRIPT);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(Variables.INITIAL_MESSAGE);
		String response;

		try {

			response = reader.readLine();
			if (response.equals("1")) {

				// Export and generate files
				runScript(Variables.DOWNLOADER_SCRIPT);

				// Get differences on the saved files
				runScript(Variables.DIFF_CALCULATOR_SCRIPT);

				// Get description and extras data
				getExtraInfo();

				// Create a new file
				Boolean createdEither = createNewFile(Variables.FINAL_RECORD_FOLDER, getNewName());

				// Save changes due to operation
				final File folder = new File(Variables.DIFF_FOLDER);
				insertHeaderOnFile();
				saveDifferences(folder);

				// Restart all
				runScript(Variables.RESTART_ALL_SCRIPT);
				System.out.println(Variables.FINALL_MESSAGE);

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
			System.out.println("... " + pathScript + " " + Variables.OK_MESSAGE);

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

				System.out.println(Variables.CHANGES_ON_FILE + nameFile);

				insertNewLineInFile(Variables.FINAL_RECORD_FOLDER, "\n \n========== " + nameFile + " ==========");

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

		String name = recordInfoMap.get(Variables.PROCESS_NAME_INDEX).split(":")[1].replaceAll("\\s", "-");
		String date = ACTUAL_DATE.replace("-", "").substring(0, 13);

		FILE_NAME = name + "_" + date + ".js";
		return FILE_NAME;

	}

	public static String getActualDate() {

		String date = null;
		try {

			Date myDate = new Date();
			SimpleDateFormat mdyFormat = new SimpleDateFormat(Variables.FORMAT_DATE);
			date = mdyFormat.format(myDate);
			;

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return date;
	}

	/////////////////////////////////
	//// PRIVATE METHODS
	/////////////////////////////////

	private static void getFileContain(String path) {

		File file = new File(path);
		try {

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = ("\n" + scanner.nextLine());
				insertNewLineInFile(Variables.FINAL_RECORD_FOLDER, line);
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

	private static void getExtraInfo() {

		BufferedReader reader = null;

		try {

			recordInfoMap.put(Variables.CREATION_DATE_INDEX, Variables.CREATION_DATE_VALUE + ACTUAL_DATE);

			reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(Variables.PROCESS_NAME_MESSAGE);
			recordInfoMap.put(Variables.PROCESS_NAME_INDEX, Variables.PROCESS_NAME_VALUE + reader.readLine());

			reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(Variables.DESCRIPTION_MESSAGE);
			recordInfoMap.put(Variables.DESCRIPTION_INDEX, Variables.DESCRIPTION_VALUE + reader.readLine());

			recordInfoMap.put(Variables.SEPARATOR_INDEX, Variables.SEPARATOR_VALUE);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void insertHeaderOnFile() {

		for (Map.Entry<String, String> data : recordInfoMap.entrySet()) {

			insertNewLineInFile(Variables.FINAL_RECORD_FOLDER, "\n" + data.getValue());

		}

	}
}

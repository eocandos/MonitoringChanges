package com.eocandos.mc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.eclipse.jgit.diff.DiffFormatter;
//import org.eclipse.jgit.diff.EditList;
//import org.eclipse.jgit.diff.HistogramDiff;
//import org.eclipse.jgit.diff.RawText;
//import org.eclipse.jgit.diff.RawTextComparator;

public class TestingCode {
	
	public static String PATH_LOG_TEMP = "file.log";
	
	public static void main(String[] args) {

		insertNewLineInFile(PATH_LOG_TEMP, "Dato 3");
		System.out.println("Ok");

	}
	
    
    private static void insertNewLineInFile(String path, String line) {
    	
    	 Date myDate = new Date();
    	 SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");    	
    	 String mdy = mdyFormat.format(myDate);
    	
    	try {
    		File file = new File(path);
    		if(file.createNewFile()) {
    			 Files.write(Paths.get(path), mdy.getBytes(), StandardOpenOption.APPEND);
    		}
    	   
    	}catch (IOException e) {
    		e.getStackTrace();
    	}
    }
    
	


}

package org.textmining.naivebayes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.codehaus.plexus.util.FileUtils;

/**
 * 
 * @author Fabian
 * @version 1.0
 * Separiert die vorhandenen Texte zufaellig in 2 Teile: Trainingsdaten und Testdaten
 *
 */

public class PosDocumentSeperator {


	private static final String SEP = System.getProperty("file.separator");
	private static final String INPUT = "data/output/pool";
	private static final File TRAIN = new File("data/output/training");
	private static final File TEST = new File("data/output/to_classify");
	
	//SEPVAL := x-% Trainingsdaten
	//1 - SEPVAL := x-% Testdaten
	private static final double SEPVAL = 0.7d;
	
	public static void seperateDocuments(){
		File fileInput = new File(INPUT);
		List<File> fileList = new ArrayList<File>();		
		File file;
		int counter;
		
		for(File item : fileInput.listFiles()){
			for(File itemFiles : item.listFiles()){
				fileList.add(itemFiles);
			}
		}

		try {
			if(TRAIN.exists())FileUtils.deleteDirectory(TRAIN);
			if(TEST.exists())FileUtils.deleteDirectory(TEST);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Collections.shuffle(fileList, new Random(System.nanoTime()));
		
		counter = (int) (fileList.size() * SEPVAL);
		
		//Texte in Trainingsdaten einteilen
		for(int i = 0; i < counter; i++){
			file = fileList.get(i);
			try {
				FileUtils.copyFile(file, new File(TRAIN.getPath() + file.getPath().substring(file.getPath().indexOf(SEP, INPUT.length() - 1))));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		//Texte in Testdaten einteilen
		for(int i = counter; i < fileList.size(); i++){
			file = fileList.get(i);
			try {
				FileUtils.copyFile(file, new File(TEST.getPath()
						+ file.getPath().substring(file.getPath()
								.indexOf(SEP, INPUT.length()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

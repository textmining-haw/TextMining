package org.textmining.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.codehaus.plexus.util.FileUtils;

/**
 * 
 * Seperates the articals randomly in 2 party: trainigs-dataset and test-dataset
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */

public class DocumentSeperator {

	private static final String INPUT = "data/pool/";
	private static final File TRAIN = new File("data/input/train");
	private static final File TEST = new File("data/input/test");
	
	//SEPVAL := x-% trainings-dataset
	//1 - SEPVAL := x-% test-dataset
	private static final double SEPVAL = 0.9d;
	
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
		
		//needs to delete directories before
		try {
			if(TRAIN.exists())FileUtils.deleteDirectory(TRAIN);
			if(TEST.exists())FileUtils.deleteDirectory(TEST);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Collections.shuffle(fileList, new Random(System.nanoTime()));
		
		counter = (int) (fileList.size() * SEPVAL);
		
		//group articles in trainings-dataset 
		for(int i = 0; i < counter; i++){
			file = fileList.get(i);
			try {
				FileUtils.copyFile(file, new File(TRAIN.getPath()
						+ file.getPath().substring(file.getPath()
								.indexOf("/", INPUT.length() - 1))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//group articles in test-dataset 
		for(int i = counter; i < fileList.size(); i++){
			file = fileList.get(i);
			try {
				FileUtils.copyFile(file, new File(TEST.getPath()
						+ file.getPath().substring(file.getPath()
								.indexOf("/", INPUT.length()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

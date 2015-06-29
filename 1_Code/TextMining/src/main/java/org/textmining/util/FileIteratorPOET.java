package org.textmining.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class FileIteratorPOET implements Iterator<File> {

	private File folder;
	private String temp = "";
	private ArrayList<String> fileArray;
	private int count;
	
	public FileIteratorPOET(String path){
		folder = new File(path);
		fileArray = new ArrayList<String>();
		count = 0;
		listFilesForFolder(folder);
	}
	  
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return count < fileArray.size();
	}

	@Override
	public File next() {
		// TODO Auto-generated method stub
		File f = new File((String) fileArray.get(count));
		count++;
		return f;
	}

	  public void listFilesForFolder(final File folder) {

	    for (final File fileEntry : folder.listFiles()) {
	      if (fileEntry.isDirectory()) {
	        // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
	        listFilesForFolder(fileEntry);
	      } else {
	        if (fileEntry.isFile()) {
	          temp = fileEntry.getName();
	          if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("txt"))
	            System.out.println("File= " + folder.getAbsolutePath()+ File.separatorChar + fileEntry.getName());
	          fileArray.add(folder.getAbsolutePath()+ File.separatorChar + fileEntry.getName());
	        }

	      }
	    }
	  }


}

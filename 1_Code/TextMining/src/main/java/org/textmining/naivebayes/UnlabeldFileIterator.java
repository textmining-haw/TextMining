package org.textmining.naivebayes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import cc.mallet.types.Instance;

public class UnlabeldFileIterator implements Iterator<Instance> {

	private File folder;
	private String temp = "";
	private ArrayList<String> fileArray;
	private int count;
	
	public UnlabeldFileIterator(String path){
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
	public Instance next() {
		// TODO Auto-generated method stub
		File f = new File((String) fileArray.get(count));
		count++;
		return new Instance(f.getAbsolutePath(), "", f.getName(), f.getAbsolutePath());

	}

	  public void listFilesForFolder(final File folder) {

	    for (final File fileEntry : folder.listFiles()) {
	      if (fileEntry.isDirectory()) {
	        // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
	        listFilesForFolder(fileEntry);
	      } else {
	        if (fileEntry.isFile()) {
	          temp = fileEntry.getName();
	          if ((temp.substring(temp.lastIndexOf('.') + 4, temp.length()).toLowerCase()).equals("pos"))
	            System.out.println("File= " + folder.getAbsolutePath()+ File.separatorChar + fileEntry.getName());
	          fileArray.add(folder.getAbsolutePath()+ File.separatorChar + fileEntry.getName());
	        }

	      }
	    }
	  }


}

package org.textmining.naivebayes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.mallet.types.Instance;

public class LabeldFileIterator implements Iterator<Instance> {

	private File folder;
	private String temp = "";
	private ArrayList<String> fileArray;
	private int count;
	
	public LabeldFileIterator(String path){
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
		//System.out.println(f.getAbsolutePath());
		String r1 = ".*negative.*";
		String r2 = ".*positive.*";
		Pattern p1 = Pattern.compile(r1);
		Pattern p2 = Pattern.compile(r2);
		Matcher m1 = p1.matcher(f.getAbsolutePath());
		Matcher m2 = p2.matcher(f.getAbsolutePath());
		
		if(m1.matches()){
			return new Instance(f.getAbsolutePath(), "negative", f.getName(), f.getAbsolutePath());
		}else if(m2.matches()){
			return new Instance(f.getAbsolutePath(), "positive", f.getName(), f.getAbsolutePath());
		}else{
			System.out.println("UNDEF::::::::: " + f.getAbsolutePath());
			return new Instance(f.getAbsolutePath(), "undef", f.getName(), f.getAbsolutePath());
		}
		
		
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

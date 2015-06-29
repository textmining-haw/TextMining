package org.textmining.naivebayes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MNNaiveBayesClassifierWriter {
	
	private static final String FILE_PATH = "data/model.ser";
	private FileOutputStream fout = null;
	private ObjectOutputStream oos = null;
	private FileInputStream fin = null;
	private ObjectInputStream ois = null;
	
	public MNNaiveBayesClassifierWriter() {
		try {
			fout = new FileOutputStream(FILE_PATH);
			oos = new ObjectOutputStream(fout);
			fin = new FileInputStream(FILE_PATH);
			ois = new ObjectInputStream(fin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeModel(MNNaiveBayesClassifier classifier) {
		try {
			oos.writeObject(classifier);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MNNaiveBayesClassifier loadClassifier() {
		MNNaiveBayesClassifier classifier = null;
		try {
			classifier = (MNNaiveBayesClassifier) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return classifier;
	}
	
}

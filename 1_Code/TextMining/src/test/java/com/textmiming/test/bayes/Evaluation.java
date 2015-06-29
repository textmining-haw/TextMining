package com.textmiming.test.bayes;

import java.util.ArrayList;

import org.textmining.naivebayes.PosDocumentSeperator;

import com.textmiming.test.bayes.ClassificationTest.Feature;

public class Evaluation {


	
	public Evaluation() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void featureDrivenTest(int rounds, ArrayList<Feature> filterdFeatures, int ngram_size){
		for(int i = 0; i < rounds; i++){
			System.out.println("Subround " + (i+1) + " of " + rounds);
			PosDocumentSeperator.seperateDocuments();
			MNNaiveBayesTests.testFilteredFeatures(filterdFeatures, ngram_size);
		}
		
	}
	

}

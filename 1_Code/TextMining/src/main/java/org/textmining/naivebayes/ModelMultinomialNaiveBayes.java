package org.textmining.naivebayes;

import java.util.ArrayList;

import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class ModelMultinomialNaiveBayes {

	private double[] prior;
	private double[][] condProb;
	private ArrayList<String> classes;
	private ArrayList<Token> vocabulary;
	private final int minWordLengthVocabluar = 1;
	private ArrayList<String> posTags;
	
	public ModelMultinomialNaiveBayes(ArrayList<Instance> trainingSet, ArrayList<String> pennTreebankPOStags) {
		// TODO Auto-generated constructor stub
		posTags = pennTreebankPOStags;
		setup(trainingSet);
	}
	
	private void setup(ArrayList<Instance> trainingSet){
		classes = new ArrayList<String>();
		
		for (Instance i : trainingSet) {
			if (!classes.contains(i.getTarget().toString().toLowerCase())) {
			classes.add(i.getTarget().toString().toLowerCase());
			}
			
		}
		
		prior = new double[classes.size()];
		setVocabular(trainingSet); 
		condProb = new double[vocabulary.size()][classes.size()];
	}
	
	/**
	 * Extracts all words of the document
	 * pos-tags specified in pennTreebankPOStags.
	 * @param pennTreebankPOStags
	 */
	private void setVocabular(ArrayList<Instance> trainingSet){
		vocabulary = new ArrayList<Token>();
		
		ArrayList<String> ignoreDuplicates = new ArrayList<String>();
		
		for (Instance inst : trainingSet) {
			TokenSequence ts = (TokenSequence) inst.getData();
			
	
			for (int i = 0; i < ts.size(); i++) {
						Token tok = ts.get(i); // ts.get(i - 1)
						if (tok.getText().length() >= minWordLengthVocabluar && !ignoreDuplicates.contains(tok.getText().toLowerCase())) {
							vocabulary.add(tok);
							ignoreDuplicates.add(tok.getText().toLowerCase());
						}
			}
		}
		
	}
	
	public int sizeOfLabels(){
		return classes.size();
	}
	
	public String getLabel(int i){
		return classes.get(i);
	}
	
	public void setPrior(int labelIndex, double val){
		prior[labelIndex] = val;
	}
	
	public double getPrior(int labelIndex){
		return prior[labelIndex];
	}
	
	public int getMinWordLength(){
		return minWordLengthVocabluar;
	}
	
	public ArrayList<String> getPOStags(){
		return posTags;
	}
	
	public void setCondProb(int tokenIdx, int classIdx, double val){
		condProb[tokenIdx][classIdx] = val;
	}
	
	public double getCondProb(int tokenIdx, int classIdx){
		return condProb[tokenIdx][classIdx];
	}
	
	public ArrayList<Token> getVocablurary(){
		return vocabulary;
	}
	

}

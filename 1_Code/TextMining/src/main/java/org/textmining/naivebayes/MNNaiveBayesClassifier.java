package org.textmining.naivebayes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class MNNaiveBayesClassifier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9060190841058586311L;
	ModelMultinomialNaiveBayes model;
	private HashMap<String, Double> classifications = new HashMap<String, Double>();
	
	public MNNaiveBayesClassifier(ModelMultinomialNaiveBayes m) {
		// TODO Auto-generated constructor stub
		model = m;
	}
	
	public String classify(Instance instance){
			ArrayList<Token> tokens = extractTokensFromDoc(instance);
			double[] score = new double[model.sizeOfLabels()];
			// System.out.println("tokens.size: " + tokens.size() +
			// " condprob.length: " + condProb.length);
			for (int i = 0; i < model.sizeOfLabels(); i++) {
				score[i] = Math.log(model.getPrior(i));
				//score[i] = model.getPrior(i);
				// System.out.println("score["+ i + "]: " + score[i]);
				for (int j = 0; j < tokens.size(); j++) {
					// System.out.println("tokens.get(j): " + tokens.get(j));
					if (tokens.get(j) != null) {
						score[i] += Math.log(model.getCondProb(j, i));
						//score[i] += model.getCondProb(j, i);
						// System.out.println("Math.log: " +
						// Math.log(condProb[j][i]) + " condProb: " +
						// condProb[j][i]);
					}
				}
			}
			
//			String shortname = Utility.getInstanceShortname(instance);
			String classification = (score[0] > score[1] ? model.getLabel(0) : model.getLabel(1));
			classifications.put(model.getLabel(0), score[0]);
			classifications.put(model.getLabel(1), score[1]);
//			System.out.println("Instance: " + shortname + " is " + classification);
//			
//		
//			for(int i = 0; i < model.sizeOfLabels(); i++){		
//				System.out.println(shortname + " " + score[i] + " " + model.getLabel(i));
//			}
//			
//			System.out.println("");
			return classification;
		}
	
	private ArrayList<Token> extractTokensFromDoc(Instance instance) {
		// TODO Auto-generated method stub
		ArrayList<Token> result = new ArrayList<Token>();
		ArrayList<Token> vocabulary = model.getVocablurary();
		TokenSequence ts = (TokenSequence) instance.getData();
		
		
		for(int i = 0; i < vocabulary.size(); i++){
			result.add(null);
		}

		ArrayList<String> allreadyInside = new ArrayList<String>();
		
		for (Token token : ts) {
			for (int i = 0; i < vocabulary.size(); i++) {
				if (vocabulary.get(i).getText().toLowerCase()
						.equals(token.getText().toLowerCase())
						&& !allreadyInside.contains(token.getText()
								.toLowerCase())) {
					allreadyInside.add(token.getText().toLowerCase());
					result.remove(i);
					result.add(i, token);

					// result.add(token);
				}
			}
		}
		//System.out.println("result.size: " + result.size());
		return result;
	}
	
	public double getPositiveRatingInPercent(){
		double total = classifications.get("positive") + classifications.get("negative");
		return 100 * (classifications.get("positive") / total);
	}
	
	public double getNegativeRatingInPercent(){
		double total = classifications.get("positive") + classifications.get("negative");
		return 100 * (classifications.get("negative") / total);
	}

}

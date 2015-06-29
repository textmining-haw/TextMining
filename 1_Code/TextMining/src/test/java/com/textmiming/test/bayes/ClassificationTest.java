package com.textmiming.test.bayes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ClassificationTest {

	public ClassificationTest() {
		// TODO Auto-generated constructor stub
	}

	public static final int ROUNDS_PER_FEATURE_SET = 20;
	public static final int NGRAM_SIZE = 2;
	private static ArrayList<String> posTags;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB", "PERSON", "LOCATION", "NULL"));
		
		XLS xls = new XLS();
		String fileName = "NaiveBayes_Tests";
		String sheetName = "Tests";
		int rowIdx = 0;
		int cellIdx = 0;
		int testNr;
		
		xls.newSheet(sheetName);
		xls.writeInCell(sheetName, rowIdx, cellIdx, "Testnr.");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Features");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Runden");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Trainingsdokumente");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Testdokumente");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Precision Class Positive");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Recall Class Positive");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "F1-Score Class Positive");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Precision Class Negative");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Recall Class Negative");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "F1-Score Class Negative");
		xls.writeInCell(sheetName, rowIdx, ++cellIdx, "Correct classified");

		
		cellIdx = 0;
		
		ArrayList<Feature> features = new ArrayList<Feature>(Arrays.asList(
				Feature.LOCATION, Feature.PERSON, Feature.NGRAM, Feature.VERB));
		List<List<Feature>> filteredFeatures = powerset(features);
		
		
		for (testNr = 0; testNr < filteredFeatures.size(); testNr++) {
			System.out.println("ROUND: " + (testNr+1) + " of " + filteredFeatures.size());
			Evaluation.featureDrivenTest(ROUNDS_PER_FEATURE_SET,
					(ArrayList<Feature>) filteredFeatures.get(testNr), NGRAM_SIZE);
			
			
			rowIdx++;
		
			xls.writeInCell(sheetName, rowIdx, cellIdx, testNr);
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, getFeatures((ArrayList<Feature>) filteredFeatures.get(testNr)));
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, ROUNDS_PER_FEATURE_SET);
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getTotalTrainingDocs() / ROUNDS_PER_FEATURE_SET);
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getTotalTestDocs() / ROUNDS_PER_FEATURE_SET);
			xls.writeInCell(sheetName, rowIdx, ++cellIdx,"" + MNNaiveBayesTests.getPrecisionClassPositive());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getRecallClassPositive());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getF1ScoreClassPositive());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx,"" + MNNaiveBayesTests.getPrecisionClassNegative());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getRecallClassNegative());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getF1ScoreClassNegative());
			xls.writeInCell(sheetName, rowIdx, ++cellIdx, "" + MNNaiveBayesTests.getCorrect() / ROUNDS_PER_FEATURE_SET);

			
			cellIdx = 0;
			MNNaiveBayesTests.reset();
			
		}
		
		xls.generateFile(fileName);

	}
	
	private static String getFeatures(ArrayList<Feature> filteredFeatures){
		String result = "";
		HashMap<String, String> noAbbreviations = new HashMap<String, String>();
		noAbbreviations.put("CC", "Coordinating Conjunction");
		noAbbreviations.put("CD", "Cardinal number");
		noAbbreviations.put("DT", "Determiner");
		noAbbreviations.put("EX", "Existential there");
		noAbbreviations.put("FW ", "Foreign word");
		noAbbreviations.put("IN", "Preposition or subordinating conjunction");
		noAbbreviations.put("JJ", "Adjective");
		noAbbreviations.put("JJR", "Adjective");
		noAbbreviations.put("JJS", "Adjective");
		noAbbreviations.put("LS", "List item marker");
		
		noAbbreviations.put("MD", "Modal");
		noAbbreviations.put("NN", "Noun");
		noAbbreviations.put("NNS", "Noun");
		noAbbreviations.put("NNP", "Noun");
		noAbbreviations.put("NNPS", "Noun");
		noAbbreviations.put("PDT", "Predeterminer");
		noAbbreviations.put("POS", "Possessive ending");
		noAbbreviations.put("PRP", "Personal pronoun");
		noAbbreviations.put("PRP$", "Possessive pronoun");
		noAbbreviations.put("RB", "Adverb");
		noAbbreviations.put("RBR", "Adverb");
		noAbbreviations.put("RBS", "Adverb");
		
		noAbbreviations.put("RP", "Particle");
		noAbbreviations.put("SYM", "Symbol");
		noAbbreviations.put("TO", "to");
		noAbbreviations.put("UH", "Interjection");
		noAbbreviations.put("VB", "Verb");
		noAbbreviations.put("VBD", "Verb");
		noAbbreviations.put("VBG", "Verb");
		noAbbreviations.put("VBN", "Verb");
		noAbbreviations.put("VBP", "Verb");
		noAbbreviations.put("VBZ", "Verb");
		noAbbreviations.put("WDT", "Wh-determiner");
		noAbbreviations.put("WP", "Wh-pronoun");
		noAbbreviations.put("WP$", "Possessive wh-pronoun");
		noAbbreviations.put("WRB", "Wh-adverb");
		noAbbreviations.put("PERSON","PERSON");
		noAbbreviations.put("LOCATION", "LOCATION");
		ArrayList<String> tagsViewed = new ArrayList<String>();
		
		
		for(String posTag : posTags){
			if((matchVerb(posTag) && filteredFeatures.contains(Feature.VERB)) ){
				continue;
			}else if(matchLocation(posTag) && filteredFeatures.contains(Feature.LOCATION)){
				continue;
			}else if(matchPerson(posTag) && filteredFeatures.contains(Feature.PERSON)){
				continue;
			}else if(matchNull(posTag)){
				continue;
			}else{
				if(!tagsViewed.contains(noAbbreviations.get(posTag))){
					
					tagsViewed.add(noAbbreviations.get(posTag));
				}
				//result+=posTag + ";" + System.getProperty("line.separator");
			}
				
		}
		
		if(filteredFeatures.contains(Feature.NGRAM)){
			//result+="NGRAM("+NGRAM_SIZE+")" + System.getProperty("line.separator");
			tagsViewed.add("NGRAM("+NGRAM_SIZE+")");
		}
		int i = 0;
		for(String t : tagsViewed){
			result+=t + "; ";
			i++;
			if(i % 4 == 0){
				result+= System.getProperty("line.separator");
			}
		}
		
		return result;
	}
	
	public static boolean matchVerb(String token) {
		if (token.matches("VB") || token.matches("VBD") || token.matches("VBG")
				|| token.matches("VBN") || token.matches("VBP")
				|| token.matches("VBZ")) {
			return true;
		}

		return false;
	}
	
	public static boolean matchLocation(String token){
		if(token.matches("LOCATION")){
			return true;
		}
		
		return false;
	}
	
	public static boolean matchPerson(String token){
		if(token.matches("PERSON")){
			return true;
		}
		
		return false;
	}
	
	public static boolean matchNull(String token){
		if(token.matches("NULL")){
			return true;
		}
		
		return false;
	}

	/**
	 * Source: http://rosettacode.org/wiki/Power_set#Java
	 * @param list
	 * @return
	 */
	public static <T> List<List<T>> powerset(Collection<T> list) {
		List<List<T>> ps = new ArrayList<List<T>>();
		ps.add(new ArrayList<T>()); // add the empty set

		// for every item in the original list
		for (T item : list) {
			List<List<T>> newPs = new ArrayList<List<T>>();

			for (List<T> subset : ps) {
				// copy all of the current powerset's subsets
				newPs.add(subset);

				// plus the subsets appended with the current item
				List<T> newSubset = new ArrayList<T>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}

			// powerset is now powerset of list.subList(0, list.indexOf(item)+1)
			ps = newPs;
		}
		return ps;
	}

	/**
	 * Keine Adjekte da diese standardmäßig betrachtet werden
	 * 
	 * @author Francis
	 *
	 */
	public enum Feature {
		LOCATION, PERSON, VERB, NGRAM;
	}

}

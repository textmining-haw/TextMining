package org.textmining.naivebayes;

import java.util.ArrayList;
import java.util.Arrays;

import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class MultinomialNaiveBayes {

	private ArrayList<Token> vocabulary;
//	private int nGramSize;

	public MultinomialNaiveBayes() {
		
	}

	/**
	 * Filters no Words out of the Instances.
	 * 
	 * @param trainingSet
	 * @return
	 */
	public MNNaiveBayesClassifier train(ArrayList<Instance> trainingSet) {

		ModelMultinomialNaiveBayes model = new ModelMultinomialNaiveBayes(
				trainingSet, new ArrayList<String>());
		vocabulary = model.getVocablurary();
		MNNaiveBayesClassifier classifier = new MNNaiveBayesClassifier(
				buildModel(trainingSet, model.getPOStags(), model));
		return classifier;
	}

	/**
	 * Trains a multinomial naive bayes classifier.
	 * 
	 * @param trainingSet
	 * @param pennTreebankPOStags
	 * @return
	 */
	public MNNaiveBayesClassifier train(ArrayList<Instance> trainingSet,
			ArrayList<String> pennTreebankPOStags) {

		ModelMultinomialNaiveBayes model = new ModelMultinomialNaiveBayes(
				trainingSet, pennTreebankPOStags);
		vocabulary = model.getVocablurary();
		MNNaiveBayesClassifier classifier = new MNNaiveBayesClassifier(
				buildModel(trainingSet, model.getPOStags(), model));

		return classifier;
	}

	private ModelMultinomialNaiveBayes buildModel(
			ArrayList<Instance> trainingSet,
			ArrayList<String> pennTreebankPOStags,
			ModelMultinomialNaiveBayes model) {

		double n = countDocs(trainingSet);

		for (int i = 0; i < model.sizeOfLabels(); i++) {

			double n1 = countDocsInClass(trainingSet, model.getLabel(i));
			model.setPrior(i, n1 / n);
			ArrayList<Token> textC = concatenateTextOfAllDocsInClass(
					trainingSet, model, model.getLabel(i));

			ArrayList<Integer> tCt = new ArrayList<Integer>();
			for (int j = 0; j < vocabulary.size(); j++) {
				tCt.add(countTokensOfTerm(textC, vocabulary.get(j)));
			}

			for (int j = 0; j < vocabulary.size(); j++) {
				model.setCondProb(j, i, countCondProb(j, i, tCt));
			}

		}

		return model;

	}

	public static int countDocs(ArrayList<Instance> instances) {
		return instances.size();
	}

	public static int countDocsInClass(ArrayList<Instance> instance,
			String klasse) {
		int result = 0;

		for (Instance i : instance) {
			if (((String) i.getTarget().toString()).toLowerCase().equals(
					klasse.toLowerCase())) {
				result++;
			}
		}

		return result;
	}

	public ArrayList<Token> concatenateTextOfAllDocsInClass(
			ArrayList<Instance> trainingSet, ModelMultinomialNaiveBayes model, String klasse) {
		ArrayList<Token> result = new ArrayList<Token>();

		for (Instance inst : trainingSet) {
			if (inst.getTarget().toString().toLowerCase().equals(klasse)) {
				TokenSequence ts = (TokenSequence) inst.getData();
		
				int j = 1;
					for (int i = 0; i < ts.size(); i++) {
//						Token t = ts.get(i);
						if (j % 2 == 0) {
//							String s = t.getText().toUpperCase();
							//if (!match(s, model.getPOStags())) {
								Token tok = ts.get(i - 1);
//								String[] singleTokens = tok.getText().split(" ");
//								tok.setText(filterPOStags(singleTokens));
								if (tok.getText().length() >=  model.getMinWordLength()) {
									//System.out.println(tok.getText());
									result.add(tok);

								}
							//}
						}
						j++;
					}
			}
		}

		return result;
	}

	
	@SuppressWarnings("unused")
	private boolean match(String token, ArrayList<String> posTags) {

		if (posTags.isEmpty())
			return false;

		for (String posTag : posTags) {
			if (token.matches(posTag)) {
				return true;
			}
		}

		return false;
	}

	private int countTokensOfTerm(ArrayList<Token> text, Token token) {
		int result = 0;

		for (Token tok : text) {
			if (tok.getText().toLowerCase()
					.equals(token.getText().toLowerCase())) {
				result++;
			}
		}

		return result;
	}

	public double countCondProb(int tokenIdx, int classIdx,
			ArrayList<Integer> tct) {
		double condProbTct = tct.get(tokenIdx) + 1;
		double sum = 0;
		// System.out.println("jo");
		for (int k = 0; k < tct.size(); k++) {
			// System.out.println("k: " + k);
			sum = sum + tct.get(k) + 1;
		}

		return condProbTct / sum;
	}

	
	@SuppressWarnings("unused")
	private String filterPOStags(String[] singleTokens) {
		String result = "";

		
		ArrayList<String> posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB"));
		
		for(int i = 0; i < singleTokens.length; i++){
			if(!posTags.contains(singleTokens[i].toUpperCase())){
				result += singleTokens[i];
				
				if(i + 1 < singleTokens.length){
					result += " ";
				}
			}
		}

		return result;
	}

}

package org.textmining.naivebayes.pipes;

import java.util.ArrayList;
import java.util.Arrays;

import org.textmining.naivebayes.Utility;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class NGram extends Pipe {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nGramSize;

	public NGram(int nGram) {
		// TODO Auto-generated constructor stub
		nGramSize = nGram;
	}

	public NGram(Alphabet dataDict, Alphabet targetDict) {
		super(dataDict, targetDict);
		// TODO Auto-generated constructor stub
	}

	public Instance pipe(Instance carrier) {
		TokenSequence ts = (TokenSequence) carrier.getData();
		ArrayList<Token> tokens = new ArrayList<Token>();

		for (int i = 0; i + nGramSize < ts.size(); i = Utility.getLastIdx() + 1) {
			Token t = Utility.getNGram(i, ts, nGramSize);
			// System.out.println(t.getText());

			t.setText(filterPOStags(t.getText().split(" ")));

		

		}

		carrier.setData(new TokenSequence(tokens));

		return carrier;

	}

	

	private String filterPOStags(String[] singleTokens) {
		String result = "";

		ArrayList<String> posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB"));

		for (int i = 0; i < singleTokens.length; i++) {
			if (!posTags.contains(singleTokens[i].toUpperCase())) {
				result += singleTokens[i];

				if (i + 1 < singleTokens.length) {
					result += " ";
				}
			}
		}

		return result;
	}

}

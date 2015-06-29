package org.textmining.naivebayes.pipes;

import java.util.ArrayList;
import java.util.Arrays;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class FilterPOSandBIOChunkerTags extends Pipe {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8632935177150178751L;

	public FilterPOSandBIOChunkerTags() {
		// TODO Auto-generated constructor stub
	}

	public FilterPOSandBIOChunkerTags(Alphabet dataDict, Alphabet targetDict) {
		super(dataDict, targetDict);
		// TODO Auto-generated constructor stub
	}
	
	public Instance pipe(Instance carrier) {
		TokenSequence ts = (TokenSequence)carrier.getData();
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		for(Token t : ts){
			
			//System.out.println(t.getText());
			
			//t.setText(filterPOStags(t.getText().split(" ")));
			
			Token token = new Token(filterPOStags(t.getText().split(" ")));
			
			String noWhitespaceToken = removeRedundantWhitespace(token.getText());
			if (noWhitespaceToken.length() > 1) {
//				System.out.println(noWhitespaceToken + " == " + t.getText());
				token.setText(noWhitespaceToken);
				tokens.add(token);
			}
			
			
		}
		
		carrier.setData(new TokenSequence(tokens));
		
		return carrier;
		
	}
	
	public String removeRedundantWhitespace(String t) {

		String result = "";
		String text = t.trim();

		ArrayList<String> signs = new ArrayList<String>();
		for (int i = 0; i < text.length(); i++) {
			if (i + 1 < text.length()) {
				signs.add(text.substring(i, i + 1));
			} else {
				signs.add(text.substring(i));
			}
		}

		int whiteSpaceCounter = 0;
		int maxWhiteSpace = 0;

		for (String s : signs) {
			if (s.matches("\\s")) {
				whiteSpaceCounter++;
			} else {
				whiteSpaceCounter = 0;
			}
			if (whiteSpaceCounter <= maxWhiteSpace) {
				result += s;
			}
		}

		return result;
	}
	
	private String filterPOStags(String[] singleTokens) {
		String result = "";

		
		ArrayList<String> posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB", "PERSON", "LOCATION", "NULL"));
		
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

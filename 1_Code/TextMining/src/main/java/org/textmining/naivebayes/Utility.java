package org.textmining.naivebayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class Utility {
	
	private static int nGrams = 0;
	private static int i = 0;
	private static HashMap<FilterType, String> noAbbreviations;
	private static HashMap<FilterType, ArrayList<String>> tags;
	private static ArrayList<String> posTags;
	private static boolean init = false;
	
	public static void init(){
		posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB", " "));
		
		tags = new HashMap<FilterType, ArrayList<String>>();
		tags.put(FilterType.CC, new ArrayList<String>(Arrays.asList("CC")));
		tags.put(FilterType.CD, new ArrayList<String>(Arrays.asList("CD")));
		tags.put(FilterType.EX, new ArrayList<String>(Arrays.asList("EX")));
		tags.put(FilterType.FW, new ArrayList<String>(Arrays.asList("FW")));
		tags.put(FilterType.IN, new ArrayList<String>(Arrays.asList("IN")));
		tags.put(FilterType.JJ, new ArrayList<String>(Arrays.asList("JJ", "JJR", "JJS")));
		tags.put(FilterType.LS, new ArrayList<String>(Arrays.asList("LS")));
		tags.put(FilterType.MD, new ArrayList<String>(Arrays.asList("MD")));
		tags.put(FilterType.NN, new ArrayList<String>(Arrays.asList("NN", "NNS", "NNP", "NNPS")));
		tags.put(FilterType.PDT, new ArrayList<String>(Arrays.asList("PDT")));
		tags.put(FilterType.POS, new ArrayList<String>(Arrays.asList("POS")));
		tags.put(FilterType.PRP, new ArrayList<String>(Arrays.asList("PRP")));
		tags.put(FilterType.PRP$, new ArrayList<String>(Arrays.asList("PRP\\$")));
		tags.put(FilterType.RB, new ArrayList<String>(Arrays.asList("RB", "RBR", "RBS")));
		tags.put(FilterType.RP, new ArrayList<String>(Arrays.asList("RP")));
		tags.put(FilterType.SYM, new ArrayList<String>(Arrays.asList("SYM")));
		tags.put(FilterType.TO, new ArrayList<String>(Arrays.asList("TO")));
		tags.put(FilterType.UH, new ArrayList<String>(Arrays.asList("UH")));
		tags.put(FilterType.VB, new ArrayList<String>(Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ")));
		tags.put(FilterType.WDT, new ArrayList<String>(Arrays.asList("WDT")));
		tags.put(FilterType.WP, new ArrayList<String>(Arrays.asList("WP")));
		tags.put(FilterType.WP$, new ArrayList<String>(Arrays.asList("WP\\$")));
		tags.put(FilterType.WRB, new ArrayList<String>(Arrays.asList("WRB")));
		tags.put(FilterType.NULL, new ArrayList<String>(Arrays.asList("NULL")));
		tags.put(FilterType.PERSON, new ArrayList<String>(Arrays.asList("PERSON")));
		tags.put(FilterType.LOCATION, new ArrayList<String>(Arrays.asList("LOCATION")));
		
		
		//Human readable descriptions for the test xls-file.
		noAbbreviations = new HashMap<FilterType, String>();
		noAbbreviations.put(FilterType.CC, "Coordinating Conjunction");
		noAbbreviations.put(FilterType.CD, "Cardinal number");
		noAbbreviations.put(FilterType.DT, "Determiner");
		noAbbreviations.put(FilterType.EX, "Existential there");
		noAbbreviations.put(FilterType.FW, "Foreign word");
		noAbbreviations.put(FilterType.IN, "Preposition or subordinating conjunction");
		noAbbreviations.put(FilterType.JJ, "Adjective");
		noAbbreviations.put(FilterType.LS, "List item marker");
		
		noAbbreviations.put(FilterType.MD, "Modal");
		noAbbreviations.put(FilterType.NN, "Noun");
		noAbbreviations.put(FilterType.NNS, "Noun");
		noAbbreviations.put(FilterType.NNP, "Noun");
		noAbbreviations.put(FilterType.NNPS, "Noun");
		noAbbreviations.put(FilterType.PDT, "Predeterminer");
		noAbbreviations.put(FilterType.POS, "Possessive ending");
		noAbbreviations.put(FilterType.PRP, "Personal pronoun");
		noAbbreviations.put(FilterType.PRP$, "Possessive pronoun");
		noAbbreviations.put(FilterType.RB, "Adverb");;
		
		noAbbreviations.put(FilterType.RP, "Particle");
		noAbbreviations.put(FilterType.SYM, "Symbol");
		noAbbreviations.put(FilterType.TO, "to");
		noAbbreviations.put(FilterType.UH, "Interjection");
		noAbbreviations.put(FilterType.VB, "Verb");
		noAbbreviations.put(FilterType.WDT, "Wh-determiner");
		noAbbreviations.put(FilterType.WP, "Wh-pronoun");
		noAbbreviations.put(FilterType.WP$, "Possessive wh-pronoun");
		noAbbreviations.put(FilterType.WRB, "Wh-adverb");
		noAbbreviations.put(FilterType.PERSON,"PERSON");
		noAbbreviations.put(FilterType.LOCATION, "LOCATION");
		noAbbreviations.put(FilterType.NULL, "NULL");
	}
	
	public static Token getNGram(int tokenIdx, TokenSequence ts, int nGramSize){
		Token result;
	
		String text =""; // ts.get(tokenIdx).getText();
		
		if(ts.size() >= tokenIdx + nGramSize){
			i = nextIdx(ts, tokenIdx);
			text += " ";
//			System.out.println("idx: " + idx + " tokenIdx + nGramSize: " + (tokenIdx + nGramSize));
			for(; i < ts.size() && nGrams != nGramSize; i = nextIdx(ts, ++i), nGrams++){
				text += ts.get(i).getText() + " ";
			}
		}
		nGrams = 0;
		//System.out.println(text);
		result = new Token(text);
		
		return result;
	}
	
	private static int nextIdx(TokenSequence ts, int idx){
		int res = ts.size();
		
		ArrayList<String> posTags = new ArrayList<String>(Arrays.asList("CC",
				"CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD",
				"NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB",
				"RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG",
				"VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB", " "));
		
		for(int i = idx; i < ts.size(); i++){
			if(!posTags.contains(ts.get(i).getText().toUpperCase())){
				res = i;
				break;
			}
		}
		
		return res;
	}
	
	public static String getInstanceShortname(Instance instance){
		String longname;
		String shortname;
		
		longname = instance.getSource().toString();
		shortname = longname.substring(longname.lastIndexOf("\\") + 1, longname.length());
		
		return shortname;
	}
	
	public static int getLastIdx(){
		return i;
	}
	
	public static Instance pipe(Instance carrier, FilterType filter){
		if(!init){
			init();
		}
		TokenSequence ts = (TokenSequence) carrier.getData();
		ArrayList<Token> tokens = new ArrayList<Token>();
		//System.out.println("size: " + ts.size());
		for (int i = 1; i < ts.size(); i+=2) {
			String token = ts.get(i).getText().toUpperCase();
		
			
				if (!(filter(filter, token))) {		
					tokens.add(new Token(ts.get(i - 1).getText()));
				}
		}
		
		carrier.setData(new TokenSequence(tokens));
		return carrier;
	}
	
	public static boolean filter(FilterType filter, String token){
		for(int i = 0; i < tags.get(filter).size(); i++){
			if(token.matches(tags.get(filter).get(i))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean pOSTag(String token){
		for(int i = 0; i < posTags.size(); i++){
			if(token.matches(posTags.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public enum FilterType {
		CC,CD,DT,EX,FW,IN,JJ,LS,MD,NN,NNS,NNP,NNPS,PDT,POS,PRP,PRP$,RB,
		RP,SYM,TO,VB, WDT,WP,WP$,WRB,UH,PERSON,LOCATION, NULL;
	}
	
	

}

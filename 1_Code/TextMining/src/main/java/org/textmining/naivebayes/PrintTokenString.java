package org.textmining.naivebayes;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class PrintTokenString extends Pipe {

	private static final long serialVersionUID = -7147982410514605124L;

	public PrintTokenString() {
		// TODO Auto-generated constructor stub
	}

	public PrintTokenString(Alphabet dataDict, Alphabet targetDict) {
		super(dataDict, targetDict);
		// TODO Auto-generated constructor stub
	}
	
	public Instance pipe(Instance carrier) {
		TokenSequence ts = (TokenSequence)carrier.getData();
		
		for(Token t : ts){
			System.out.println(t.getText() + " (pipe: " + PrintTokenString.class);
		}
		
		return carrier;
		
	}
	

}

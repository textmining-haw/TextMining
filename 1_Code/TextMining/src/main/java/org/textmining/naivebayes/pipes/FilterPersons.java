package org.textmining.naivebayes.pipes;

import org.textmining.naivebayes.Utility;
import org.textmining.naivebayes.Utility.FilterType;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;

public class FilterPersons extends Pipe {

	/**
	 * 
	 */
	private static final long serialVersionUID = 860063356165025632L;

	public FilterPersons() {
		// TODO Auto-generated constructor stub
	}

	public FilterPersons(Alphabet dataDict, Alphabet targetDict) {
		super(dataDict, targetDict);
		// TODO Auto-generated constructor stub
	}

	public Instance pipe(Instance carrier) {
		return Utility.pipe(carrier, FilterType.PERSON);
	}

}

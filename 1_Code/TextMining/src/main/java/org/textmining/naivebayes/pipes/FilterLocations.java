package org.textmining.naivebayes.pipes;

import org.textmining.naivebayes.Utility;
import org.textmining.naivebayes.Utility.FilterType;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;

public class FilterLocations extends Pipe {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5366372311794874731L;

	public FilterLocations() {
		// TODO Auto-generated constructor stub
	}

	public FilterLocations(Alphabet dataDict, Alphabet targetDict) {
		super(dataDict, targetDict);
		// TODO Auto-generated constructor stub
	}

	public Instance pipe(Instance carrier) {
		return Utility.pipe(carrier, FilterType.LOCATION);
	}

}

package org.textmining.naivebayes.pipes;

import java.util.ArrayList;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Filename2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.types.Instance;

import com.textmiming.test.bayes.ClassificationTest.Feature;

public class PipeSequence {

	ArrayList<Feature> features = null;

	public PipeSequence() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param features
	 *            Features die nicht im Text bleiben sollen.
	 */
	public PipeSequence(ArrayList<Feature> features) {
		this.features = features;
	}

	/**
	 * 
	 * @param instances
	 *            to manipulate
	 * @return instances manipulated instances
	 */
	public ArrayList<Instance> prepare(ArrayList<Instance> instances,
			int ngram_size) {
		if (features == null) {
			ArrayList<Instance> result = new ArrayList<Instance>();

			ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
			pipeList.add(new Filename2CharSequence());
			pipeList.add(new Target2Label());
			pipeList.add(new CharSequence2TokenSequence());
			pipeList.add(new TokenSequenceLowercase());
			pipeList.add(new FilterVerbs());
			pipeList.add(new FilterLocations());
			pipeList.add(new FilterPersons());
			pipeList.add(new FilterPOSandBIOChunkerTags());
			pipeList.add(new NGram(ngram_size));
			// pipeList.add(new PrintTokenString());

			for (Instance i : instances) {

				for (Pipe p : pipeList) {
					i = p.pipe(i);
				}

				result.add(i);
			}

			return result;
		} else {
			ArrayList<Instance> result = new ArrayList<Instance>();

			ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
			pipeList.add(new Filename2CharSequence());
			pipeList.add(new Target2Label());
			pipeList.add(new CharSequence2TokenSequence());
			pipeList.add(new TokenSequenceLowercase());
	
			if (features.contains(Feature.VERB)) {
				pipeList.add(new FilterVerbs());
			}

			if (features.contains(Feature.LOCATION)) {
				pipeList.add(new FilterLocations());
			}

			if (features.contains(Feature.PERSON)) {
				pipeList.add(new FilterPersons());
			}

			pipeList.add(new FilterPOSandBIOChunkerTags());
			
			if (features.contains(Feature.NGRAM)) {
				pipeList.add(new NGram(ngram_size));
			}


			for (Instance i : instances) {

				for (Pipe p : pipeList) {
					i = p.pipe(i);
				}

				result.add(i);
			}

			return result;
		}
	}

}

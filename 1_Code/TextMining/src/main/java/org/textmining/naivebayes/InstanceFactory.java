package org.textmining.naivebayes;

import java.util.ArrayList;

import cc.mallet.types.Instance;

public class InstanceFactory {

	private final String INPUT_PATH_TRAINING = "data/output/training";
	private final String INPUT_PATH_CLASSIFICATION = "data/output/to_classify";
	
	public InstanceFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Instance> getTrainingSet(){
		ArrayList<Instance> training_set = new ArrayList<Instance>();
		LabeldFileIterator iterator = new LabeldFileIterator(INPUT_PATH_TRAINING);
		
		while(iterator.hasNext()){
			training_set.add(iterator.next());
		}
		
		return training_set;
	}
	
	public ArrayList<Instance> getTestingSet(){
		ArrayList<Instance> testing_set = new ArrayList<Instance>();
		LabeldFileIterator iterator = new LabeldFileIterator(INPUT_PATH_CLASSIFICATION);
		
		while(iterator.hasNext()){
			testing_set.add(iterator.next());
		}

		
		return testing_set;
	}
	

}

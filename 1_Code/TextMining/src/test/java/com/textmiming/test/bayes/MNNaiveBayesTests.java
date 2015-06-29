package com.textmiming.test.bayes;

import java.util.ArrayList;

import org.textmining.naivebayes.InstanceFactory;
import org.textmining.naivebayes.MNNaiveBayesClassifier;
import org.textmining.naivebayes.MNNaiveBayesClassifierWriter;
import org.textmining.naivebayes.MultinomialNaiveBayes;
import org.textmining.naivebayes.pipes.PipeSequence;

import cc.mallet.types.Instance;

import com.textmiming.test.bayes.ClassificationTest.Feature;

public class MNNaiveBayesTests {

	private static double medianPrecision = 0;
	private static double testSetDocAmount = 0;
	private static double rightCorrect = 0; // indicates the right classified
											// document amount
	private static double rightPositivePerRound = 0;
	private static double testDocsPerRound = 0;
	private static double positiveDocCount = 0;
	private static double negativeDocCount = 0;

	private static double truePositiveClassPositive = 0;
	private static double falsePositiveClassPositive = 0;
	private static double falseNegativeClassPositive = 0;

	private static double truePositiveClassNegative = 0;
	private static double falsePositiveClassNegative = 0;
	private static double falseNegativeClassNegative = 0;

	private static MNNaiveBayesClassifier classifier = null;
	// private static double correct = 0;
	private static double trainingsDocAmount = 0;
	private static MNNaiveBayesClassifierWriter fileWriter = new MNNaiveBayesClassifierWriter();

	public static void testFilteredFeatures(ArrayList<Feature> features,
			int ngram_size) {

		InstanceFactory factory = new InstanceFactory();
		PipeSequence pipes = new PipeSequence(features);

		ArrayList<String> posTags = new ArrayList<String>();
		ArrayList<Instance> trainingSet = pipes.prepare(
				factory.getTrainingSet(), ngram_size);
		ArrayList<Instance> testSet = pipes.prepare(factory.getTestingSet(),
				ngram_size);

		MultinomialNaiveBayes naiveBayes = new MultinomialNaiveBayes();
		classifier = naiveBayes.train(trainingSet, posTags);
		//fileWriter.writeModel(classifier);

		trainingsDocAmount += trainingSet.size();
		int correct = 0;

		String classification = "";

		for (Instance i : testSet) {
			if (i.getTarget().toString().equals("positive")) {
				positiveDocCount++;
			} else if (i.getTarget().toString().equals("positive")) {
				negativeDocCount++;
			}
		}

		//classifier = fileWriter.loadClassifier();
		for (Instance i : testSet) {
			classification = classifier.classify(i);
			if (classification.equals(i.getTarget().toString())) {
				correct++;
			}

			// MNNaiveBayesTests.correct = correct;

			if (classification.equals(i.getTarget().toString())
					&& classification.equals("negative")) {
				truePositiveClassNegative++;
			} else if (classification.equals(i.getTarget().toString())
					&& classification.equals("positive")) {
				truePositiveClassPositive++;
			} else if (classification.equals("positive")
					&& i.getTarget().toString().equals("negative")) {
				falsePositiveClassPositive++;
				falseNegativeClassNegative++;
			} else if (classification.equals("negative")
					&& i.getTarget().toString().equals("positive")) {
				falsePositiveClassNegative++;
				falseNegativeClassPositive++;
			}

			if (classification.equals("negative")) {
				negativeDocCount++;
			} else if (classification.equals("positive")) {
				positiveDocCount++;
			}

		}

		rightPositivePerRound = correct;
		testDocsPerRound = testSet.size();
		rightCorrect += correct;
		testSetDocAmount += testSet.size();
		medianPrecision = (rightCorrect * 100) / testSetDocAmount;
	}

	public static double getTotalTestDocs() {
		return testSetDocAmount;
	}

	
	/**
	 * Liefert die Anzahl korrekt klassifizierter Dokumente, über alle Runden
	 * gemittelt, in Prozent.
	 * 
	 * @return
	 */
	public static double getMedianCorrectClassifiedDocCounts() {
		return medianPrecision;
	}

	public static double getTotalTrainingDocs() {
		return trainingsDocAmount;
	}

	/**
	 * Liefert die Anzahl der, in der aktuellen Runde, korrekt klassifizierten
	 * Dokumente.
	 * 
	 * @return
	 */
	public static double getRightPositivePerRound() {
		return rightPositivePerRound;
	}

	/**
	 * Liefert die Anzahl der, in der aktuellen Runde klassifizierten,
	 * Dokumente.
	 * 
	 * @return
	 */
	public static double getTestDocsPerRound() {
		return testDocsPerRound;
	}

	/**
	 * precision (positive predictive value)= rightPostive / (rightPositive +
	 * falseNegative) Quelle: http://en.wikipedia.org/wiki/Precision_and_recall
	 * 
	 * @return
	 */
	public static double getPrecisionClassPositive() {
		 if(truePositiveClassPositive == 0 && falsePositiveClassPositive == 0)
		 return 0;
		//
		return truePositiveClassPositive
				/ (truePositiveClassPositive + falsePositiveClassPositive);
	}

	public static double getPrecisionClassNegative() {
		 if(truePositiveClassNegative == 0 && falsePositiveClassNegative == 0)
		 return 0;
		//
		return truePositiveClassNegative
				/ (truePositiveClassNegative + falsePositiveClassNegative);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static double getRecallClassPositive() {
		 if(truePositiveClassPositive == 0 && falseNegativeClassPositive == 0)
		 return 0;

		return truePositiveClassPositive
				/ (truePositiveClassPositive + falseNegativeClassPositive);
	}

	public static double getRecallClassNegative() {
		 if(truePositiveClassNegative == 0 && falseNegativeClassNegative == 0)
		 return 0;

		return truePositiveClassNegative
				/ (truePositiveClassNegative + falseNegativeClassNegative);
	}

	public static double getPositiveDocCount() {
		return positiveDocCount;
	}

	public static double getNegativeDocCount() {
		return negativeDocCount;
	}

	public static double getTruePositiveClassPositive() {
		return truePositiveClassPositive;
	}

	public static double getFalsePositiveClassPositive() {
		return falsePositiveClassPositive;
	}

	public static double getTruePositiveClassNegative() {
		return truePositiveClassNegative;
	}

	public static double getFalsePositiveClassNegative() {
		return falsePositiveClassNegative;
	}

	public static MNNaiveBayesClassifier getClassifier() {
		return classifier;
	}

	public static double getF1ScoreClassPositive() {
		return 2
				* getTruePositiveClassPositive()
				/ (2 * getTruePositiveClassPositive()
						+ getFalsePositiveClassPositive() + getFalseNegativeClassPositive());
	}

	public static double getF1ScoreClassNegative() {
		return 2
				* getTruePositiveClassNegative()
				/ (2 * getTruePositiveClassNegative()
						+ getFalsePositiveClassNegative() + getFalseNegativeClassNegative());
	}

	public static void reset() {
		medianPrecision = 0;
		testSetDocAmount = 0;
		rightCorrect = 0; // indicates the right classified document amount
		rightPositivePerRound = 0;
		testDocsPerRound = 0;
		positiveDocCount = 0;
		negativeDocCount = 0;
		truePositiveClassPositive = 0;
		falsePositiveClassPositive = 0;
		falseNegativeClassPositive = 0;

		truePositiveClassNegative = 0;
		falsePositiveClassNegative = 0;
		falseNegativeClassNegative = 0;

		classifier = null;
		// correct = 0;
		trainingsDocAmount = 0;
	}

	public static double getFalseNegativeClassPositive() {
		return falseNegativeClassPositive;
	}

	public static double getFalseNegativeClassNegative() {
		return falseNegativeClassNegative;
	}

	public static double getCorrect() {
		// TODO Auto-generated method stub
		return rightCorrect;
	}
}

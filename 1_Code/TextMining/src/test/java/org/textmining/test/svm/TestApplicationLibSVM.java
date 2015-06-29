package org.textmining.test.svm;

import java.io.File;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.uima.fit.testing.util.HideOutput;
import org.pmw.tinylog.Logger;
import org.textmining.annotator.SvmDocumentClassificationAnnotator;
import org.textmining.svm.RunModel;
import org.textmining.svm.TrainModel;
import org.textmining.util.DocumentSeperator;

/**
 * Our test-application which train a model and test some articles for the
 * extractors and features we want to use with the dataset which we generate
 * randomly. Futhermore we can choose how many time we want to generate a new
 * dataset.
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */
public class TestApplicationLibSVM {

	public static int currentCombination = 1;
	private final static int maxCombination = 10;
	public static boolean DEBUG = false;

	public void run(int shuffleIterations) throws Exception {

		for (int sh_iter = 1; sh_iter <= shuffleIterations; sh_iter++) {

			DocumentSeperator.seperateDocuments();

			int amountPosText = new File("data/input/train/positive")
					.listFiles().length;
			int amountNegText = new File("data/input/train/negative")
					.listFiles().length;

			Logger.info("========================================");
			Logger.info("=========New Shuffle Iteration: " + sh_iter);
			Logger.info("========================================");
			Logger.info("Anzahl der positiven Texte zum Trainieren: "
					+ amountPosText);
			Logger.info("Anzahl der negativen Texte zum Trainieren: "
					+ amountNegText);
			Logger.info("Anzahl aller Texte zum Trainieren: "
					+ (amountPosText + amountNegText));

			for (int comb_iter = 1; comb_iter <= maxCombination; comb_iter++) {

				SvmDocumentClassificationAnnotator
						.setCurrentExtractorComination(comb_iter);

				TestApplicationLibSVM.printCombinationLineToLogfile(comb_iter);
				String[] args = {};
				long start = System.currentTimeMillis();
				HideOutput hider = null;

				// hide Output when debug off
				if (!TestApplicationLibSVM.DEBUG) {
					hider = new HideOutput();
				}
				TrainModel.main(args);
				Logger.info("Ergebnis der Klassifikation:");
				RunModel.main(args);

				// restore output
				if (!TestApplicationLibSVM.DEBUG) {
					hider.restoreOutput();
				}

				long end = System.currentTimeMillis() - start;

				String formattedDuration = DurationFormatUtils
						.formatDurationHMS(end);
				Logger.info("\n\n*********************Duration: "
						+ formattedDuration + "*********************");

				currentCombination++;
			}
		}
	}

	private static void printCombinationLineToLogfile(int combination) {
		switch (combination) {
		case 1:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, TypePathExtractor");
			Logger.info("Features: None");
			break;

		case 2:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, TypePathExtractor");
			Logger.info("Features: Part-Of-Speech (JJ, JJS, JJR)");
			break;

		case 3:
			Logger.info("Type: CombinedDocumentAnnotationExtractor");
			Logger.info("Extractors: CountAnnotationExtractor<Sentence>, CountAnnotationExtractor<Token>");
			Logger.info("Features: None");
			break;

		case 4:
			Logger.info("Type: CombinedDocumentAnnotationExtractor");
			Logger.info("Extractors: CountAnnotationExtractor<Sentence>, CountAnnotationExtractor<Token>");
			Logger.info("Features: Part-Of-Speech (JJ, JJS, JJR)");
			break;

		case 5:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, FeatureFunctionExtractor(TypePathExtractor, LowerCaseFunction, NGramFeatureExtractor(Right-to-left, 0, 3))");
			Logger.info("Features: None");
			break;

		case 6:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, FeatureFunctionExtractor(TypePathExtractor, LowerCaseFunction, NGramFeatureExtractor(Right-to-left, 0, 3))");
			Logger.info("Features: Part-Of-Speech (JJ, JJS, JJR)");
			break;

		case 7:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, FeatureFunctionExtractor(TypePathExtractor, LowerCaseFunction, NGramFeatureExtractor(Right-to-left, 0, 3), NGramFeatureExtractor(Left-to-Right, 0, 3))");
			Logger.info("Features: None");
			break;

		case 8:
			Logger.info("Type: CombinedTokenExtractor");
			Logger.info("Extractors: CoveredText2TokenExtractor, FeatureFunctionExtractor(TypePathExtractor, LowerCaseFunction, NGramFeatureExtractor(Right-to-left, 0, 3), NGramFeatureExtractor(Left-to-Right, 0, 3))");
			Logger.info("Features: Part-Of-Speech (JJ, JJS, JJR)");
			break;

		case 9:
			Logger.info("Type: nGramExtractor");
			Logger.info("Extractors: nGramExtractor auf token");
			Logger.info("Features:  None");
			break;

		case 10:
			Logger.info("Type: nGramExtractor");
			Logger.info("Extractors: nGramExtractor auf token");
			Logger.info("Features: Part-Of-Speech (JJ, JJS, JJR)");
			break;
		}
	}

	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals("yes")) {
			TestApplicationLibSVM.DEBUG = true;
		}
		int shuffleIteration = 1;
		TestApplicationLibSVM testApplication = new TestApplicationLibSVM();
		try {
			testApplication.run(shuffleIteration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

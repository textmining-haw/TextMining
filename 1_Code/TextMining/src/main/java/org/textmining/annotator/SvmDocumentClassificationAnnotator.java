package org.textmining.annotator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.Feature;
import org.cleartk.ml.Instance;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Focus;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Ngram;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Preceding;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.CombinedExtractor1;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;
import org.cleartk.ml.feature.extractor.TypePathExtractor;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction.Orientation;
import org.cleartk.ml.feature.function.FeatureFunctionExtractor;
import org.cleartk.ml.feature.function.LowerCaseFeatureFunction;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.textmining.svm.type.UsenetDocument;

/**
 * Defines our main-annotator in which we declare some extractors and features
 * we want to use.
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */
public class SvmDocumentClassificationAnnotator extends
		CleartkAnnotator<String> {

	private CombinedExtractor1<Token> combinedTokenExtractor;
	private CombinedExtractor1<DocumentAnnotation> combinedDocumentAnnotationExtractor;
	private CleartkExtractor<Token, Token> nGramExtractor;

	private static int currentExtractorComination = 1;
	/**
	 * Initialize a POS-TypePathExtractor.
	 * 
	 * @return The extractor.
	 */
	private FeatureExtractor1<Token> initTypePathtExtractor() {
		return new TypePathExtractor<Token>(Token.class, "pos");
	}

	/**
	 * Initialize a CoveredTextExtractor, which look at the two annotations to
	 * the left of the focus annotation.
	 * 
	 * @return The extractor.
	 */
	private CleartkExtractor<Token, Token> initCoveredText2TokensExtractor() {
		return new CleartkExtractor<Token, Token>(Token.class,
				new CoveredTextExtractor<Token>(), new Preceding(2));

	}

	/**
	 * Initialize a simple CleartkExtractor which contains a
	 * POS-TypePathExtracotor and a 1-Gram.
	 * 
	 * @return The extractor.
	 */
	private CleartkExtractor<Token, Token> initNGramExtractor() {
		return new CleartkExtractor<Token, Token>(Token.class,
				new TypePathExtractor<Token>(Token.class, "pos"), new Ngram(
						new Preceding(1), new Focus()));
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		DocumentAnnotation doc = (DocumentAnnotation) jCas
				.getDocumentAnnotationFs();
		List<Feature> lf = new ArrayList<Feature>();

		List<FeatureExtractor1<Token>> lt = new ArrayList<FeatureExtractor1<Token>>();
		List<FeatureExtractor1<DocumentAnnotation>> lda = new ArrayList<FeatureExtractor1<DocumentAnnotation>>();

		List<Feature> adjectiveFeatures = new ArrayList<Feature>(Arrays.asList(
				new Feature("part-of-speech", "JJ"), new Feature(
						"part-of-speech", "JJS"), new Feature("part-of-speech",
						"JJR")));

		// in dependence on the combination we are training/testing at the
		// moment, we choose the extractors and features we want to use.
		switch (SvmDocumentClassificationAnnotator.currentExtractorComination) {
		case 1:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(initTypePathtExtractor());
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
				}
			}
			break;

		case 2:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(initTypePathtExtractor());
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
					lf.addAll(adjectiveFeatures);
				}
			}
			break;

		case 3:
			lda.add(new CountAnnotationExtractor<DocumentAnnotation>(
					Sentence.class));
			lda.add(new CountAnnotationExtractor<DocumentAnnotation>(
					Token.class));
			this.combinedDocumentAnnotationExtractor = new CombinedExtractor1<>(
					lda);
			lf.addAll(this.combinedDocumentAnnotationExtractor.extract(jCas,
					doc));
			break;

		case 4:
			lda.add(new CountAnnotationExtractor<DocumentAnnotation>(
					Sentence.class));
			lda.add(new CountAnnotationExtractor<DocumentAnnotation>(
					Token.class));
			this.combinedDocumentAnnotationExtractor = new CombinedExtractor1<>(
					lda);
			lf.addAll(this.combinedDocumentAnnotationExtractor.extract(jCas,
					doc));
			lf.addAll(adjectiveFeatures);
			break;

		case 5:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(new FeatureFunctionExtractor<Token>(
					initTypePathtExtractor(), new LowerCaseFeatureFunction(),
					new CharacterNgramFeatureFunction(
							Orientation.RIGHT_TO_LEFT, 0, 3)));
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
				}
			}
			break;

		case 6:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(new FeatureFunctionExtractor<Token>(
					initTypePathtExtractor(), new LowerCaseFeatureFunction(),
					new CharacterNgramFeatureFunction(
							Orientation.RIGHT_TO_LEFT, 0, 3)));
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
					lf.addAll(adjectiveFeatures);
				}
			}
			break;

		case 7:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(new FeatureFunctionExtractor<Token>(
					initTypePathtExtractor(), new LowerCaseFeatureFunction(),
					new CharacterNgramFeatureFunction(
							Orientation.RIGHT_TO_LEFT, 0, 3),
					new CharacterNgramFeatureFunction(
							Orientation.LEFT_TO_RIGHT, 0, 3)));
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
				}
			}
			break;

		case 8:
			lt.add(initCoveredText2TokensExtractor());
			lt.add(new FeatureFunctionExtractor<Token>(
					initTypePathtExtractor(), new LowerCaseFeatureFunction(),
					new CharacterNgramFeatureFunction(
							Orientation.RIGHT_TO_LEFT, 0, 3),
					new CharacterNgramFeatureFunction(
							Orientation.LEFT_TO_RIGHT, 0, 3)));
			this.combinedTokenExtractor = new CombinedExtractor1<Token>(lt);
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.combinedTokenExtractor.extract(jCas, token));
					lf.addAll(adjectiveFeatures);
				}
			}
			break;

		case 9:
			this.nGramExtractor = initNGramExtractor();
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.nGramExtractor.extract(jCas, token));
				}
			}
			break;

		case 10:
			this.nGramExtractor = initNGramExtractor();
			for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
				for (Token token : JCasUtil.selectCovered(jCas, Token.class,
						sentence)) {
					lf.addAll(this.nGramExtractor.extract(jCas, token));
					lf.addAll(adjectiveFeatures);
				}
			}
			break;
		}

		Instance<String> instance = new Instance<String>();
		// add all created features to our instances we want to write to a file
		// for training or get then for testing
		instance.addAll(lf);

		if (isTraining()) {
			UsenetDocument document = JCasUtil.selectSingle(jCas,
					UsenetDocument.class);
			instance.setOutcome(document.getCategory());
			this.dataWriter.write(instance);

		} else {
			List<Feature> features = instance.getFeatures();
			// call the classify-method of our libsvm
			String result = this.classifier.classify(features);
			UsenetDocument document = new UsenetDocument(jCas, 0, jCas
					.getDocumentText().length());
			document.setCategory(result);

			// Logger.warn("Test 1: " + ViewUriUtil.getURI(jCas) + " | " +
			// result);

			// iterate above score-set and save the probabilities of the input
			for (Entry<?, ?> item : this.classifier.score(features).entrySet()) {

				// Logger.warn("Test 2.1: " + ViewUriUtil.getURI(jCas) + " | " +
				// item.getKey());
				// Logger.warn("Test 2.2: " + ViewUriUtil.getURI(jCas) + " | " +
				// item.getValue());

				if (item.getKey().equals("positive")) {

					// Logger.warn("Test 3.1: " + ViewUriUtil.getURI(jCas) +
					// " | " + item.getKey());
					// Logger.warn("Test 3.2: " + ViewUriUtil.getURI(jCas) +
					// " | " + item.getValue());

					document.setProbabilityPositiveKey((String) item.getKey());
					document.setProbabilityPositiveValue((Double) item
							.getValue());
				} else {

					// Logger.warn("Test 3.3: " + ViewUriUtil.getURI(jCas) +
					// " | " + item.getKey());
					// Logger.warn("Test 3.4: " + ViewUriUtil.getURI(jCas) +
					// " | " + item.getValue());

					document.setProbabilityNegativeKey((String) item.getKey());
					document.setProbabilityNegativeValue((Double) item
							.getValue());
				}
			}

			document.addToIndexes();
		}

	}

	/**
	 * Little extractor which extracts the DocumentAnnotations (e.g. sentences
	 * or tokens) and count them.
	 * 
	 * @author David Laule und Fabian Reiber (HAW Hamburg)
	 * @version 1.0
	 *
	 * @param <T>
	 *            e.g. DocumentAnnotation
	 */
	public static class CountAnnotationExtractor<T extends Annotation>
			implements NamedFeatureExtractor1<T> {

		private Class<? extends Annotation> annotationType;

		private String name;

		public CountAnnotationExtractor(
				Class<? extends Annotation> annotationType) {
			this.annotationType = annotationType;
			this.name = "Count_" + this.annotationType.getName();
		}

		@Override
		public String getFeatureName() {
			return this.name;
		}

		@Override
		public List<Feature> extract(JCas view, Annotation focusAnnotation)
				throws CleartkExtractorException {
			List<?> annotations = JCasUtil.selectCovered(this.annotationType,
					focusAnnotation);
			return Arrays.asList(new Feature(this.name, annotations.size()));
		}
	}
	
	public static void setCurrentExtractorComination(int currentExtractorComination) {
		SvmDocumentClassificationAnnotator.currentExtractorComination = currentExtractorComination;
	}

}

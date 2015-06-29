package org.textmining.svm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.ViewTextCopierAnnotator;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.eval.AnnotationStatistics;
import org.cleartk.eval.Evaluation_ImplBase;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.jar.JarClassifierBuilder;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.textmining.annotator.GoldDocumentCategoryAnnotator;
import org.textmining.annotator.StanfordCoreNlpAnnotator;
import org.textmining.annotator.SvmDocumentClassificationAnnotator;
import org.textmining.casconsumer.ExamplePosPlainTextWriter;
import org.textmining.casconsumer.LibSvmStringOutcomeDataWriter;
import org.textmining.casconsumer.PrintClassificationsAnnotator;

import com.lexicalscope.jewel.cli.Option;

/**
 * Defines and run the pipeline for training- and test-mode.
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */
public class SvmDocumentClassificationEvaluation extends
		Evaluation_ImplBase<File, AnnotationStatistics<String>> {

	private List<String> trainingArguments;

	public static final String GOLD_VIEW_NAME = "DocumentClassificationGoldView";

	public interface Options {
		@Option(longName = "train-dir", description = "Specify the directory containing the training documents.  This is used for cross-validation, and for training in a holdout set evaluation. "
				+ "When we run this example we point to a directory containing training data from a subset of the 20 newsgroup corpus - i.e. a directory called '3news-bydate/train'", defaultValue = "data/input/train")
		public File getTrainDirectory();

		@Option(longName = "test-dir", description = "Specify the directory containing the test (aka holdout/validation) documents.  This is for holdout set evaluation. "
				+ "When we run this example we point to a directory containing training data from a subset of the 20 newsgroup corpus - i.e. a directory called '3news-bydate/test'", defaultValue = "data/input/test")
		public File getTestDirectory();

		@Option(longName = "models-dir", description = "specify the directory in which to write out the trained model files", defaultValue = "target/svm_classification_advanced/models")
		public File getModelsDirectory();

		@Option(longName = "training-args", description = "specify training arguments to be passed to the learner.  For multiple values specify -ta for each - e.g. '-ta -t -ta 0'", defaultValue = {
				"-s", "0", "-t", "0" })
		public List<String> getTrainingArguments();
	}

	public SvmDocumentClassificationEvaluation(File baseDirectory) {
		super(baseDirectory);
		this.trainingArguments = Arrays.<String> asList();
	}

	public SvmDocumentClassificationEvaluation(File baseDirectory,
			List<String> trainingArguments) {
		super(baseDirectory);
		this.trainingArguments = trainingArguments;
	}

	/**
	 * Not needed for our work.
	 */
	@Deprecated
	@Override
	protected AnnotationStatistics<String> test(CollectionReader arg0, File arg1)
			throws Exception {
		return null;
	}

	/**
	 * Replacement for test-method above. Creates and run the pipeline for
	 * test-mode.
	 * 
	 * @param collectionReader
	 *            The CollectionReader witch reads the input.
	 * @param outputFileDir
	 *            Where to place the model while training and where to find it
	 *            while testing.
	 * @throws Exception
	 *             Some exception which can occure.
	 */
	protected void classify(CollectionReader collectionReader,
			File outputFileDir) throws Exception {

		AggregateBuilder builder = createDocumentClassificationAggregate(
				outputFileDir, AnnotatorMode.TEST);

		SimplePipeline.runPipeline(collectionReader, builder
				.createAggregateDescription(), AnalysisEngineFactory
				.createEngineDescription(PrintClassificationsAnnotator.class));

	}

	/**
	 * Creates and run the pipeline for training-mode.
	 * 
	 * @param reader
	 *            The CollectionReader witch reads the input.
	 * @param outputFileDir
	 *            Where to place the model while training and where to find it
	 *            while testing.
	 * @throws Exception
	 *             Some exception which can occure.
	 */
	@Override
	protected void train(CollectionReader reader, File outputFileDir)
			throws Exception {
		AggregateBuilder builder = createDocumentClassificationAggregate(
				outputFileDir, AnnotatorMode.TRAIN);

		SimplePipeline
				.runPipeline(reader, builder.createAggregateDescription());

		// if you define your own features which need to learn on their own,
		// then put this code here

		System.out.println("Train model and write model.jar file.");
		
		JarClassifierBuilder.trainAndPackage(outputFileDir,
				this.trainingArguments
						.toArray(new String[this.trainingArguments.size()]));
		
	}

	/**
	 * Creates the preprocessing-pipeline with necessary annotators for training
	 * and testing.
	 * 
	 * @param mode
	 *            Mode for train oder test.
	 * @return New AggregateBuilder, which contains the annotators for the
	 *         pipeline.
	 * @throws ResourceInitializationException
	 *             Could not add an annotator to the AggregateBuilder
	 */
	public static AggregateBuilder createPreprocessingAggregate(
			AnnotatorMode mode) throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();

		builder.add(UriToDocumentTextAnnotator.getDescription());

		// NLP pre-processing components
		// if pos-tagging not necessary, use this annotators
		// builder.add(SentenceAnnotator.getDescription());
		// builder.add(TokenAnnotator.getDescription());
		// builder.add(DefaultSnowballStemmer.getDescription("English"));

		builder.add(StanfordCoreNlpAnnotator.getDescription());

		// PosPlainTextWriter writes the .pos-files which our Naive-Bayes
		// implementation needs
		builder.add(AnalysisEngineFactory.createEngineDescription(
				ExamplePosPlainTextWriter.class,
				ExamplePosPlainTextWriter.PARAM_OUTPUT_DIRECTORY_NAME,
				"data/output/pos"));

		switch (mode) {
		case TRAIN:
			builder.add(AnalysisEngineFactory
					.createEngineDescription(GoldDocumentCategoryAnnotator.class));
			break;
		case TEST:
			// Copies the text from the default view to a separate gold view
			builder.add(AnalysisEngineFactory.createEngineDescription(
					ViewTextCopierAnnotator.class,
					ViewTextCopierAnnotator.PARAM_SOURCE_VIEW_NAME,
					CAS.NAME_DEFAULT_SOFA,
					ViewTextCopierAnnotator.PARAM_DESTINATION_VIEW_NAME,
					GOLD_VIEW_NAME));

			// If this is testing, put the document categories in the gold view
			// The extra parameters to add() map the default view to the gold
			// view.
			builder.add(
					AnalysisEngineFactory
							.createEngineDescription(GoldDocumentCategoryAnnotator.class),
					CAS.NAME_DEFAULT_SOFA, GOLD_VIEW_NAME);
			break;
		}

		return builder;
	}

	/**
	 * Add the specific SvmDocumentClassificationAnnotator for training and
	 * testing. Main difference is the that the training-mode needs to write the
	 * model and the test-mode read it.
	 * 
	 * @param modelDirectory
	 * @param mode
	 *            Mode for train oder test.
	 * @return New AggregateBuilder, which contains the annotators for the
	 *         pipeline.
	 * @throws ResourceInitializationException
	 *             Could not add an annotator to the AggregateBuilder
	 */
	public static AggregateBuilder createDocumentClassificationAggregate(
			File modelDirectory, AnnotatorMode mode)
			throws ResourceInitializationException {

		AggregateBuilder builder = createPreprocessingAggregate(mode);

		switch (mode) {
		case TRAIN:
			// For training we will create DocumentClassificationAnnotator that
			// Extracts the features as is, and then writes out the data to
			// a serialized instance file.

			builder.add(AnalysisEngineFactory.createEngineDescription(
					SvmDocumentClassificationAnnotator.class,
					DefaultDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
					LibSvmStringOutcomeDataWriter.class.getName(),
					DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
					modelDirectory.getPath()));
			break;

		case TEST:
			builder.add(AnalysisEngineFactory.createEngineDescription(
					SvmDocumentClassificationAnnotator.class,
					CleartkAnnotator.PARAM_IS_TRAINING, false,
					GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
					JarClassifierBuilder.getModelJarFile(modelDirectory)));
			break;
		}

		return builder;
	}

	@Override
	protected CollectionReader getCollectionReader(List<File> items)
			throws Exception {
		return UriCollectionReader.getCollectionReaderFromFiles(items);
	}

	/**
	 * Reads the files in the train/test-directory.
	 * 
	 * @param directory
	 *            The directory where to find the files.
	 * @return List of files.
	 */
	public static List<File> getFilesFromDirectory(File directory) {
		IOFileFilter fileFilter = FileFilterUtils
				.makeSVNAware(HiddenFileFilter.VISIBLE);
		IOFileFilter dirFilter = FileFilterUtils.makeSVNAware(FileFilterUtils
				.and(FileFilterUtils.directoryFileFilter(),
						HiddenFileFilter.VISIBLE));
		return new ArrayList<File>(FileUtils.listFiles(directory, fileFilter,
				dirFilter));
	}
}

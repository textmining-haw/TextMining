package org.textmining.naivebayes.preprocessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.cleartk.snowball.DefaultSnowballStemmer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.textmining.annotator.StanfordCoreNlpAnnotator;
import org.textmining.util.FileIteratorPOET;



public class Main {
	
	//private static boolean ISTRAINING = true;
	
	public static String INPUTPATH_FOR_TRAINING_NEGATIVE = "data/pool/negative";
	public static String INPUTPATH_FOR_TRAINING_POSITIVE = "data/pool/positive";
	public static String INPUTPATH_FOR_CLASSIFICATION = "data/input/to_classify";
	public static String OUTPUTPATH_OF_POSTAGS_NEGATIVE = "data/output/pool/negative";
	public static String OUTPUTPATH_OF_POSTAGS_POSITIVE = "data/output/pool/positive";
	public static String OUTPUTPATH_OF_POSTAGS_CLASSIFICATION = "data/output/to_classify";
	public static String OUTPUTPATH_FOR_NAMEDENTITY = "data/output/ne";
	
	public static void main(String[] args) throws UIMAException, IOException {
		long startTime = System.currentTimeMillis();

		createPOStags(INPUTPATH_FOR_TRAINING_NEGATIVE, OUTPUTPATH_OF_POSTAGS_NEGATIVE);
		createPOStags(INPUTPATH_FOR_TRAINING_POSITIVE, OUTPUTPATH_OF_POSTAGS_POSITIVE);
		
	    
	    System.out.println("Benoetigte Zeit: " + (System.currentTimeMillis() - startTime) / 1000.0 + " Sekunden.");
	}
	
	private static void createPOStags(String inputDir, String outputDir) throws UIMAException, IOException{
		// TODO Auto-generated method stub
				org.textmining.util.FileIteratorPOET iterator = new FileIteratorPOET(inputDir);
			    
				List<File> fileList = new ArrayList<File>();
			    
			    while(iterator.hasNext()){
			    	fileList.add(iterator.next());
			    }
			    
			    CollectionReader reader = UriCollectionReader.getCollectionReaderFromFiles(fileList);
			    
			    AggregateBuilder builder = new AggregateBuilder();
			    
			    builder.add(UriToDocumentTextAnnotator.getDescription()); //Path to Document
			  
			    builder.add(StanfordCoreNlpAnnotator.getDescription()); //POS-Tagger

			    //NLP pre-processing components
			    builder.add(DefaultSnowballStemmer.getDescription("English")); // Stemming
			    
			    //builder.add(PrinterAnnotator.getDescription());
			    
			    builder.add(AnalysisEngineFactory.createEngineDescription(
			            ExamplePosPlainTextWriter.class,
			            ExamplePosPlainTextWriter.PARAM_OUTPUT_DIRECTORY_NAME,
			            outputDir));
			   
			    /*builder.add(AnalysisEngineFactory.createEngineDescription(
			    		NamedEntityWriter.class,
			    		NamedEntityWriter.PARAM_OUTPUT_DIRECTORY_NAME,
			    		OUTPUTPATH_FOR_NAMEDENTITY));*/
			    
			    SimplePipeline.runPipeline(reader, builder.createAggregateDescription());
			    
		
	}
}

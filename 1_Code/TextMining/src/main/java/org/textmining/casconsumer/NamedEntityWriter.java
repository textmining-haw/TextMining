package org.textmining.casconsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ne.type.NamedEntity;
import org.cleartk.util.ViewUriUtil;

/**
 * Extract the NamedEntities from the CAS-object and write it in the specific
 * folder.
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */
public class NamedEntityWriter extends JCasAnnotator_ImplBase {

	public static final String PARAM_OUTPUT_DIRECTORY_NAME = "outputDirectoryName";

	@ConfigurationParameter(name = PARAM_OUTPUT_DIRECTORY_NAME, mandatory = true, description = "provides the directory where the named entity text files will be written")
	private String outputDirectoryName = "data/output/ne";

	protected File outputDir;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		this.outputDir = new File(outputDirectoryName);
		if (!this.outputDir.exists()) {
			this.outputDir.mkdirs();
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		String id = new File(ViewUriUtil.getURI(jCas)).getName();
		PrintWriter outputWriter;
		try {
			outputWriter = new PrintWriter(new File(this.outputDir, id + ".ne"));
		} catch (FileNotFoundException e) {
			throw new AnalysisEngineProcessException(e);
		}

		for (NamedEntity item : JCasUtil.select(jCas, NamedEntity.class)) {
			outputWriter.print("Mentions: "
					+ Arrays.deepToString(item.getMentions().toStringArray()));
			outputWriter.println();
		}

		outputWriter.close();
	}

}

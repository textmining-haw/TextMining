package org.textmining.casconsumer;

import java.text.DecimalFormat;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.cleartk.util.ViewUriUtil;
import org.pmw.tinylog.Logger;
import org.textmining.svm.type.UsenetDocument;

/**
 * Print and log the statistic-result of a test.
 * 
 * @author David Laule und Fabian Reiber (HAW Hamburg)
 * @version 1.0
 *
 */
public class PrintClassificationsAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		UsenetDocument document = JCasUtil.select(jCas, UsenetDocument.class)
				.iterator().next();
		DecimalFormat df = new DecimalFormat("####0.00");
		Logger.info(ViewUriUtil.getURI(jCas) + " as " + document.getCategory()
				+ "(" + df.format(document.getProbabilityPositiveValue() * 100)
				+ "|" + df.format(document.getProbabilityNegativeValue() * 100)
				+ ")");
	}

}

/** 
 * Copyright (c) 2007-2011, Regents of the University of Colorado 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of the University of Colorado at Boulder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package org.textmining.svm;

import java.io.File;
import java.util.List;

import org.apache.uima.collection.CollectionReader;
import org.codehaus.plexus.util.FileUtils;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

/**
 * Copyright (c) 2012, Regents of the University of Colorado <br>
 * All rights reserved. <br>
 * 
 * Illustrates how to train a simple document classification annotator. For a
 * more in-depth example that demonstrates ClearTK best practices including the
 * use of more sophisticated feature extractors and the evaluation framework
 * refer to the examples in org.cleartk.examples.document.classification
 * 
 * 
 * @author Lee Becker
 * 
 */
public class TrainModel {

	public interface Options {
		@Option(longName = "train-dir", description = "Specify the directory containing the training documents.  This is used for cross-validation, and for training in a holdout set evaluation. "
				+ "When we run this example we point to a directory containing training data from a subset of the 20 newsgroup corpus - i.e. a directory called '3news-bydate/train'", defaultValue = "data/input/train")
		public File getTrainDirectory();

		@Option(longName = "models-dir", description = "specify the directory in which to write out the trained model files", defaultValue = "target/svm_classification_advanced/models")
		public File getModelsDirectory();

		@Option(longName = "training-args", description = "specify training arguments to be passed to the learner.  For multiple values specify -ta for each - e.g. '-ta -t -ta 0'", defaultValue = {
				"-s", "0", "-t", "0" })
		public List<String> getTrainingArguments();
	}

	public static void main(String[] args) throws Exception {

		Options options = CliFactory.parseArguments(Options.class, args);

		FileUtils.deleteDirectory(options.getModelsDirectory());

		SvmDocumentClassificationEvaluation eval = new SvmDocumentClassificationEvaluation(
				options.getModelsDirectory(), options.getTrainingArguments());

		List<File> testFiles = SvmDocumentClassificationEvaluation
				.getFilesFromDirectory(options.getTrainDirectory());
		CollectionReader reader = eval.getCollectionReader(testFiles);
		eval.train(reader, options.getModelsDirectory());
	}

}

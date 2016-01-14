package org.cyberanima.iii.algorithm.classifier.weka.test;

import java.io.IOException;

import org.cyberanima.iii.algorithm.classifier.weka.NavieBayesClassifier;
import weka.classifiers.bayes.NaiveBayes;

public class NavieBayerClassifierTest {
	public static void main(String args[]) throws IOException {
		NaiveBayes nb = NavieBayesClassifier.TrainClassiferFromARFF("./testcase/algorithm/classifier/iris.arff", 4);
	}
}

package org.cyberanima.iii.algorithm.lda.mahout.test;

import java.io.IOException;

import org.cyberanima.iii.algorithm.lda.mahout.SequenceFileUtils;

public class EnglishTopicAnalyzerTest {
	public static void main(String args[]) throws IOException {
		//EnglishTopicAnalyzer.Analyze("./testcase/lda/en/docfile/", "./testcase/lda/en/seqfile/", "./testcase/lda/en/vecfile/", "./testcase/lda/en/matfile/", "./testcase/lda/en/cvbfile/");
		//EnglishTopicAnalyzer.RowID("./testcase/lda/en/vecfile/", "./testcase/lda/en/matfile/");
		//EnglishTopicAnalyzer.CVBInvocation("./testcase/en/lda/vecfile/", "./testcase/lda/en/matfile/", "./testcase/lda/en/cvbfile/");
		//SequenceFilePrinter.PrintIntTextSeqFile("./testcase/lda/en/vecfile/dictionary.file-0");
		//HashMap<IntWritable, TopTopics> docTopTopics = EnglishTopicAnalyzer.GetTopTopics(6, "./testcase/lda/en/cvbfile/");
		//EnglishTopicAnalyzer.PrintTopTopics("./testcase/lda/en/cvbfile/", docTopTopics);
		//SequenceFilePrinter.PrintIntLongSeqFile("./testcase/lda/en/vecfile/frequency.file-0");
		//SequenceFilePrinter.PrintTextVectorSeqFile("./testcase/lda/en/vecfile/tfidf-vectors/part-r-00000");	
		SequenceFileUtils.PrintTextVectorSeqFile("./testcase/lda/en/vecfile/tf-vectors/part-r-00000");	
		//SequenceFilePrinter.ReadIntVectorSeqFile("./testcase/lda/en/matfile/matrix/");
		//SequenceFilePrinter.PrintIntVectorSeqFile("./testcase/lda/en/cvbfile/text_cvb_document/part-m-00000");	
	}
}

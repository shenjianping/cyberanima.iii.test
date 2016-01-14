package org.cyberanima.iii.algorithm.lda.mahout.test;

import java.io.IOException;

import org.cyberanima.iii.algorithm.lda.mahout.SequenceFileUtils;

public class EnglishTopicAnalyzerTest {
	public static void main(String args[]) throws IOException {
		//EnglishTopicAnalyzer.Analyze("./testcase/algorithm/lda/en/docfile/", "./testcase/algorithm/lda/en/seqfile/", "./testcase/algorithm/lda/en/vecfile/", "./testcase/algorithm/lda/en/matfile/", "./testcase/algorithm/lda/en/cvbfile/");
		//EnglishTopicAnalyzer.RowID("./testcase/algorithm/lda/en/vecfile/", "./testcase/algorithm/lda/en/matfile/");
		//EnglishTopicAnalyzer.CVBInvocation("./testcase/algorithm/lda/en/vecfile/", "./testcase/algorithm/lda/en/matfile/", "./testcase/algorithm/lda/en/cvbfile/");
		//SequenceFilePrinter.PrintIntTextSeqFile("./testcase/algorithm/lda/en/vecfile/dictionary.file-0");
		//HashMap<IntWritable, TopTopics> docTopTopics = EnglishTopicAnalyzer.GetTopTopics(6, "./testcase/algorithm/lda/en/cvbfile/");
		//EnglishTopicAnalyzer.PrintTopTopics("./testcase/algorithm/lda/en/cvbfile/", docTopTopics);
		//SequenceFilePrinter.PrintIntLongSeqFile("./testcase/algorithm/lda/en/vecfile/frequency.file-0");
		//SequenceFilePrinter.PrintTextVectorSeqFile("./testcase/algorithm/lda/en/vecfile/tfidf-vectors/part-r-00000");	
		SequenceFileUtils.PrintTextVectorSeqFile("./testcase/algorithm/lda/en/vecfile/tf-vectors/part-r-00000");	
		//SequenceFilePrinter.ReadIntVectorSeqFile("./testcase/algorithm/lda/en/matfile/matrix/");
		//SequenceFilePrinter.PrintIntVectorSeqFile("./testcase/algorithm/lda/en/cvbfile/text_cvb_document/part-m-00000");	
	}
}

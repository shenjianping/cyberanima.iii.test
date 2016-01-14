package org.cyberanima.iii.algorithm.lda.mahout.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.cyberanima.iii.algorithm.lda.mahout.ChineseTopicAnalyzer;
import org.cyberanima.iii.algorithm.lda.mahout.TopTopics;

public class ChineseTopicAnalyzerTest {
	public static void main(String args[]) throws IOException {
		//ChineseTopicAnalyzer.DocSegmentFromDir("./testcase/algorithm/lda/cn/docfile/", "./testcase/algorithm/lda/cn/segDocfile/");
		//ChineseTopicAnalyzer.GenSparseVecFromSegDocDir("./algorithm/testcase/lda/cn/segDocfile/", "./testcase/algorithm/lda/cn/vecfile/");
		//ChineseTopicAnalyzer.RowID("./algorithm/testcase/lda/cn/vecfile/", "./testcase/algorithm/lda/cn/matfile/");
		//ChineseTopicAnalyzer.CVBInvocation("./algorithm/testcase/lda/cn/vecfile/", "./testcase/algorithm/lda/cn/matfile/", "./testcase/algorithm/lda/cn/cvbfile/");
		HashMap<IntWritable, TopTopics> docTopTopics = ChineseTopicAnalyzer.GetTopTopics(6, "./testcase/algorithm/lda/cn/cvbfile/");
		ChineseTopicAnalyzer.PrintTopTopics("./testcase/algorithm/lda/cn/cvbfile/", docTopTopics);
		//SequenceFileUtils.PrintTextIntSeqFile("./testcase/algorithm/lda/cn/vecfile/dictionary.file-0");
		//SequenceFileUtils.PrintIntLongSeqFile("./testcase/algorithm/lda/cn/vecfile/frequency.file-0");
		//SequenceFileUtils.PrintTextVectorSeqFile("./testcase/algorithm/lda/cn/vecfile/tf-vectors/part-r-00000");
		//SequenceFilePrinter.PrintIntTextSeqFile("./testcase/algorithm/lda/cn/vecfile/dictionary.file-0");
		//SequenceFileUtils.PrintIntVectorSeqFile("./testcase/algorithm/lda/cn/cvbfile/text_cvb_document/part-m-00000");
	}
}

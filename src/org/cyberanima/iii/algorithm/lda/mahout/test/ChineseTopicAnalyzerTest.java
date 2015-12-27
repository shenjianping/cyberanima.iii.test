package org.cyberanima.iii.algorithm.lda.mahout.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.cyberanima.iii.algorithm.lda.mahout.ChineseTopicAnalyzer;
import org.cyberanima.iii.algorithm.lda.mahout.TopTopics;

public class ChineseTopicAnalyzerTest {
	public static void main(String args[]) throws IOException {
		//ChineseTopicAnalyzer.DocSegmentFromDir("./testcase/lda/cn/docfile/", "./testcase/lda/cn/segDocfile/");
		//ChineseTopicAnalyzer.GenSparseVecFromSegDocDir("./testcase/lda/cn/segDocfile/", "./testcase/lda/cn/vecfile/");
		//ChineseTopicAnalyzer.RowID("./testcase/lda/cn/vecfile/", "./testcase/lda/cn/matfile/");
		//ChineseTopicAnalyzer.CVBInvocation("./testcase/lda/cn/vecfile/", "./testcase/lda/cn/matfile/", "./testcase/lda/cn/cvbfile/");
		HashMap<IntWritable, TopTopics> docTopTopics = ChineseTopicAnalyzer.GetTopTopics(6, "./testcase/lda/cn/cvbfile/");
		ChineseTopicAnalyzer.PrintTopTopics("./testcase/lda/cn/cvbfile/", docTopTopics);
		//SequenceFileUtils.PrintTextIntSeqFile("./testcase/lda/cn/vecfile/dictionary.file-0");
		//SequenceFileUtils.PrintIntLongSeqFile("./testcase/lda/cn/vecfile/frequency.file-0");
		//SequenceFileUtils.PrintTextVectorSeqFile("./testcase/lda/cn/vecfile/tf-vectors/part-r-00000");
		//SequenceFilePrinter.PrintIntTextSeqFile("./testcase/lda/cn/vecfile/dictionary.file-0");
		//SequenceFileUtils.PrintIntVectorSeqFile("./testcase/lda/cn/cvbfile/text_cvb_document/part-m-00000");
	}
}

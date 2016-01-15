package org.cyberanima.iii.algorithm.nlp.stanford.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cyberanima.iii.algorithm.classifier.weka.LabeledInstances;
import org.cyberanima.iii.algorithm.classifier.weka.NavieBayesClassifier;
import org.cyberanima.iii.algorithm.classifier.weka.WekaUtils;
import org.cyberanima.iii.algorithm.nlp.stanford.ChineseParser;
import org.cyberanima.iii.algorithm.nlp.stanford.SentenceInfo;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class ChineseAddressAnalysis {
	public static ArrayList<String[]> LoadUnlabeledAddress (String addressFile) {
		ArrayList<String[]> unlabeledAddress = new ArrayList<String[]>();
		
		try {
			FileInputStream f = new FileInputStream(addressFile); 
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String line = br.readLine();
			//StringBuilder sb = new StringBuilder();
			
			while (line != null ) { 
				//System.out.println(line);
				String[] splitline = line.split("\\|");
				String[] addressInfo = new String[8];
				addressInfo[0] = splitline[0];
				addressInfo[1] = splitline[1];
				addressInfo[2] = splitline[2];
				addressInfo[3] = splitline[3];
				addressInfo[4] = splitline[4];
				addressInfo[5] = splitline[1];
				
				unlabeledAddress.add(addressInfo);
				
				
				line = br.readLine();	
			}
			
			br.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return unlabeledAddress;
	}
	
	public static ArrayList<String[]> LoadLabeledAddress (String addressFile) {
		ArrayList<String[]> labeledAddress = new ArrayList<String[]>();
		
		try {
			FileInputStream f = new FileInputStream(addressFile); 
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String line = br.readLine();
			//StringBuilder sb = new StringBuilder();
			
			while (line != null ) { 
				//System.out.println(line);
				String[] splitline = line.split("\\|");
				String[] addressInfo = new String[7];
				addressInfo[0] = splitline[0];
				addressInfo[1] = splitline[1];
				addressInfo[2] = splitline[2];
				addressInfo[3] = splitline[3];
				addressInfo[4] = splitline[4];
				addressInfo[5] = splitline[1];
				addressInfo[6] = splitline[5];
				
				labeledAddress.add(addressInfo);
				
				
				line = br.readLine();	
			}
			
			br.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return labeledAddress;
	}
	
	public static ArrayList<String[]> LoadConceptnet (String conceptnetFile) {
		ArrayList<String[]> conceptnet = new ArrayList<String[]>();
		
		try {
			FileInputStream f = new FileInputStream(conceptnetFile); 
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String line = br.readLine();
			//StringBuilder sb = new StringBuilder();
			
			while (line != null ) { 
				
				//System.out.println(line);
				String[] splitline = line.split("\\|");
				
				conceptnet.add(splitline);
				
				line = br.readLine();		
			}
			
			br.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conceptnet;
	}
	
	public static String GenLabeledInstanceData(SentenceInfo sInfo, String pText) {
		System.out.println(sInfo.getSentence());
		
		String arffText = "";
		
		for (int i = 0; i < sInfo.getWordposes().size(); i++) {
			String[] segAddressWordpos = (String[]) sInfo.getWordposes().get(i);
			
			// "@ATTRIBUTE text STRING"
			arffText += segAddressWordpos[0] + ",";
			
			// "@ATTRIBUTE position NUMERIC"
			arffText += i + ",";
			
			// "@ATTRIBUTE pos {NN,NR,VV,CD,M,AD,OTHER}"
			if (segAddressWordpos[1].equals("NN") 
					|| segAddressWordpos[1].equals("NR")
					|| segAddressWordpos[1].equals("VV")
					|| segAddressWordpos[1].equals("CN")
					|| segAddressWordpos[1].equals("M")
					|| segAddressWordpos[1].equals("AD")) {
				arffText += segAddressWordpos[1] + ",";		
			}
			else {
				arffText += "OTHER,";	
			}
			
			// "@ATTRIBUTE gfNode {NP,VP,QP,CLP,ADVP,OTHER}"
			String gfNodeAtt = "";
			List<Tree> nodelist = sInfo.getTree().preOrderNodeList();
			for (int j = 0; j < nodelist.size(); j++) {
				Tree curNode = nodelist.get(j);
				List<Tree> cNodelist = curNode.getChildrenAsList();
				for (int k = 0; k < cNodelist.size(); k++) {
					Tree curCNode = cNodelist.get(k);
					if (curCNode.nodeString().equals(segAddressWordpos[1])) {
						List<Tree> gcNodelist = curCNode.getChildrenAsList();
						for (int l = 0; l < gcNodelist.size(); l++) {
							Tree curGCNode = gcNodelist.get(l);
							if (curGCNode.nodeString().equals(segAddressWordpos[0])) {
								if (curNode.nodeString().equals("NP") 
										|| curNode.nodeString().equals("VP")
										|| curNode.nodeString().equals("QP")
										|| curNode.nodeString().equals("CLP")
										|| curNode.nodeString().equals("ADVP")) {
									gfNodeAtt = curNode.nodeString() + ",";		
								}
								else {
									gfNodeAtt = "OTHER,";	
								}
								
							
							}
						}
					}
				}
			}
			
			arffText += gfNodeAtt;
			
			
			// "@ATTRIBUTE class {Yes,NO}\n"
			if (pText.indexOf(segAddressWordpos[0]) != -1) {
				arffText += "Yes\r\n";
			}
			else {
				arffText += "No\r\n";
			}
			
		    
			
		}
		
		return arffText;
		
	}
	
	public static void PrintSentenceInfo (SentenceInfo sInfo) {
		String addressSeg = "";
		
		//System.out.println(sInfo.getSentence());
		
		for (int i = 0; i < sInfo.getWordposes().size(); i++) {
			String[] segAddressWordpos = (String[]) sInfo.getWordposes().get(i);
			
			String word = segAddressWordpos[0].toString();
			String pos = segAddressWordpos[1].toString();
			
			addressSeg += word + "(" + pos + ")" + "\t";
		}
		
		//System.out.println(addressSeg);	
		sInfo.getTree().pennPrint();
		sInfo.getDependencies().prettyPrint();
	}
	
	public static void AddressInfoPrecal(ArrayList<String[]> cAddress, ArrayList<String[]> conceptnet) {
		for (int i = 0; i < cAddress.size(); i++) {
			String[] ca = (String[]) cAddress.get(i);
			for (int j = 0; j < conceptnet.size(); j++) {
				String[] cn = (String[]) conceptnet.get(j);
				if (cn[2].equals("isA") ) {	
					ca[5] = ca[5].replaceAll(cn[0], "");
				}
			}
			cAddress.set(i, ca);
		}
	}
	
	
	public static ArrayList<String[]> HandleUnlabeledAddress(ArrayList<String[]> unlabeledAddress, StanfordCoreNLP pipeline, NaiveBayes nb) {
		ArrayList<String[]> handledAddress = new ArrayList<String[]>();
		
		for (int i = 0; i < unlabeledAddress.size(); i++) {
			try {
				String[] addressInfo = (String[]) unlabeledAddress.get(i);
				//System.out.println(addressInfo[1]);
				System.out.println(addressInfo[5]);
				SentenceInfo sInfo = ChineseParser.SegSentence(pipeline, addressInfo[5]);	
				addressInfo[6] = ClassifyUnlabeledAddress(sInfo, nb);
				handledAddress.add(addressInfo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return handledAddress;
	}
	
	public static String ClassifyUnlabeledAddress(SentenceInfo sInfo, NaiveBayes nb) {
		Attribute attPosition = new Attribute("position");
		ArrayList<String> lblPos = new ArrayList<String>();
		lblPos.add("NN");
		lblPos.add("NR");
		lblPos.add("VV");
		lblPos.add("CD");
		lblPos.add("M");
		lblPos.add("AD");
		lblPos.add("OTHER");
		Attribute attPos = new Attribute("pos", lblPos);
		ArrayList<String> lblGfNode = new ArrayList<String>();
		lblGfNode.add("NP");
		lblGfNode.add("VP");
		lblGfNode.add("QP");
		lblGfNode.add("CLP");
		lblGfNode.add("ADVP");
		lblGfNode.add("OTHER");
		Attribute attGfNode = new Attribute("gfNode", lblGfNode);
		ArrayList<String> lblClass = new ArrayList<String>();
		lblClass.add("Yes");
		lblClass.add("No");
		Attribute attClass = new Attribute("class", lblClass);
		
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(attPosition);
		attributes.add(attPos);
		attributes.add(attGfNode);
		attributes.add(attClass);
		
		Instances unlabeled = new Instances("UnlabeledAddress", attributes, 0);
		unlabeled.setClassIndex(3);
		
		int attPositionValue = 0;
		String attPosValue = "";
		String attGfNodeValue = "";
		
		String addressCore = "";
		
		
		for (int i = 0; i < sInfo.getWordposes().size(); i++) {
			String[] segAddressWordpos = (String[]) sInfo.getWordposes().get(i);
			
			// "@ATTRIBUTE position NUMERIC"
			attPositionValue = i;
			
			// "@ATTRIBUTE pos {NN,NR,VV,CD,M,AD,OTHER}"
			if (segAddressWordpos[1].equals("NN") 
					|| segAddressWordpos[1].equals("NR")
					|| segAddressWordpos[1].equals("VV")
					|| segAddressWordpos[1].equals("CN")
					|| segAddressWordpos[1].equals("M")
					|| segAddressWordpos[1].equals("AD")) {
				attPosValue = segAddressWordpos[1];		
			}
			else {
				attPosValue = "OTHER";	
			}
			
			// "@ATTRIBUTE gfNode {NP,VP,QP,CLP,ADVP,OTHER}"
			List<Tree> nodelist = sInfo.getTree().preOrderNodeList();
			for (int j = 0; j < nodelist.size(); j++) {
				Tree curNode = nodelist.get(j);
				List<Tree> cNodelist = curNode.getChildrenAsList();
				for (int k = 0; k < cNodelist.size(); k++) {
					Tree curCNode = cNodelist.get(k);
					if (curCNode.nodeString().equals(segAddressWordpos[1])) {
						List<Tree> gcNodelist = curCNode.getChildrenAsList();
						for (int l = 0; l < gcNodelist.size(); l++) {
							Tree curGCNode = gcNodelist.get(l);
							if (curGCNode.nodeString().equals(segAddressWordpos[0])) {
								if (curNode.nodeString().equals("NP") 
										|| curNode.nodeString().equals("VP")
										|| curNode.nodeString().equals("QP")
										|| curNode.nodeString().equals("CLP")
										|| curNode.nodeString().equals("ADVP")) {
									attGfNodeValue = curNode.nodeString();		
								}
								else {
									attGfNodeValue = "OTHER";	
								}

							}
						}
					}
				}
			}
			
			double[] values = new double[unlabeled.numAttributes()];
			values[0] = attPositionValue;
			values[1] = unlabeled.attribute(1).indexOfValue(attPosValue);
			values[2] = unlabeled.attribute(2).indexOfValue(attGfNodeValue);
			
			unlabeled.add(new DenseInstance(4, values));
			
			//System.out.println(values[0] + "," + values[1] + "," + values[2] + "," + values[3]);
			//System.out.println(attPositionValue + "," + attPosValue + "," + attGfNodeValue + ",?");
			
			try {
				double dClassValue = nb.classifyInstance(unlabeled.instance(0));
				unlabeled.instance(0).setClassValue(dClassValue);
				int iClassValue = Math.round(Float.parseFloat(dClassValue + ""));
				if (unlabeled.classAttribute().value(iClassValue).equals("Yes")) {
					addressCore += segAddressWordpos[0];
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			unlabeled.clear();
			
			
		}
		
		System.out.println(addressCore);
		
		return addressCore;

	}
	
	public static void LoadLabeledAddressToARFF(ArrayList<String[]> labeledAddress, StanfordCoreNLP pipeline, String outputFile) {
		
		String arffText = "@RELATION LabeledCAddress\r\n"
				+ "@ATTRIBUTE text STRING\r\n"
				+ "@ATTRIBUTE position NUMERIC\r\n"
				+ "@ATTRIBUTE pos {NN,NR,VV,CD,M,AD,OTHER}\r\n"
				+ "@ATTRIBUTE gfNode {NP,VP,QP,CLP,ADVP,OTHER}\r\n"
				+ "@ATTRIBUTE class {Yes,No}\r\n"
				+ "@DATA\r\n";
		
		for (int i = 0; i < labeledAddress.size(); i++) {
			
			try {
				String[] addressInfo = (String[]) labeledAddress.get(i);
				System.out.println(addressInfo[5]);
				SentenceInfo sInfo = ChineseParser.SegSentence(pipeline, addressInfo[5]);	
				arffText += GenLabeledInstanceData(sInfo, addressInfo[6]);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(arffText);
		
		try {
			File of = new File(outputFile);
			FileOutputStream os = new FileOutputStream(outputFile); 
			if (!of.exists()) {
				of.createNewFile();
			}	    				
			
			byte[] contentInBytes = arffText.getBytes();
			os.write(contentInBytes);
			os.flush();
			os.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		ArrayList<String[]> unlabeledAddress = LoadUnlabeledAddress("./examplecase/algorithm/chineseaddressanalysis/unlabeledaddress.txt");
		ArrayList<String[]> labeledAddress = LoadLabeledAddress("./examplecase/algorithm/chineseaddressanalysis/labeledaddress.txt");
		ArrayList<String[]> conceptnet = LoadConceptnet("./examplecase/algorithm/chineseaddressanalysis/conceptnet.txt");
		AddressInfoPrecal(unlabeledAddress, conceptnet);
		AddressInfoPrecal(labeledAddress, conceptnet);
		StanfordCoreNLP pipeline = ChineseParser.GetPipeline();
		LoadLabeledAddressToARFF(labeledAddress, pipeline, "./examplecase/algorithm/chineseaddressanalysis/LabeledCAddress.arff");
		LabeledInstances li = WekaUtils.GetInstancesSetFromARFF("./examplecase/algorithm/chineseaddressanalysis/LabeledCAddress.arff", 2/3f, 4, 0);
		NaiveBayes nb = NavieBayesClassifier.TrainClassiferFromLabeledInstances(li);
		ArrayList<String[]> handledAddress = HandleUnlabeledAddress(unlabeledAddress, pipeline, nb);
	}
}

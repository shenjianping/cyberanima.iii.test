package org.cyberanima.iii.algorithm.nlp.stanford.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.cyberanima.iii.algorithm.nlp.stanford.ChineseParser;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ChineseNRSearch {
	public static HashMap<String, String> LoadUnitData (String cleanDataFile) {
		HashMap<String, String> unitData = new HashMap<String, String>();
		
		try {
			FileInputStream f = new FileInputStream(cleanDataFile); 
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			
			while (line != null ) { 
				String ID = line;
				line = br.readLine();
				String name = line;
				
				unitData.put(ID + ":" + name, name);
				
				line = br.readLine();
			}
			
			br.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return unitData;
	}
	
	public static HashMap<String, Integer> SearchByKeyword(String keyWord, HashMap<String, String> unitData, StanfordCoreNLP pipeline) {
		HashMap<String, Integer> searchResult = new HashMap<String, Integer>();
		
		Object[] segKeyword = ChineseParser.SegmentStringToArray(pipeline, keyWord).toArray();	
		
		Iterator iter = unitData.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			
			int count = 0;
			
			for (int i = 0; i < segKeyword.length; i++) {
				String word = segKeyword[i].toString();
				
				if (val.indexOf(word) != -1) {
					count++;
				}
			}
			
			if (count > segKeyword.length - 2) {
				searchResult.put(key, count);
			}	
		}
		
		return searchResult;
	}
	
	public static void PrintSearchResult(HashMap<String, Integer> searchResult) {
		ArrayList<HashMap.Entry<String, Integer>> sortList = new ArrayList<HashMap.Entry<String, Integer>>(searchResult.entrySet());
		
		Collections.sort(sortList, new Comparator<HashMap.Entry<String, Integer>>() {   
		    public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2) {      
		        return (o2.getValue() - o1.getValue()); 
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
		
		for (int i = 0; i < sortList.size(); i ++) {
			String key = sortList.get(i).getKey().toString();
			String val = sortList.get(i).getValue().toString();
			
			System.out.println(key + " ---- " + val);
			
			if (i > 10) {
				break;
			}
		}
	}
	
	public static void SearchViaConsoleLine(HashMap<String, String> unitData) {
		StanfordCoreNLP pipeline = ChineseParser.GetPipeline();
		
		try {
			while (true) {
				String inputText = "";
				BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
				inputText = stdin.readLine();
				HashMap<String, Integer> searchResult = SearchByKeyword(inputText, unitData, pipeline);
				PrintSearchResult(searchResult);
			}
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
	
	
	public static void main (String args[]) {
		HashMap<String, String> unitData = LoadUnitData("./examplecase/algorithm/chinesenrsearch/unitdata.txt");
		SearchViaConsoleLine(unitData);
	}
}

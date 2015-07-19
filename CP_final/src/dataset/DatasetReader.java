package dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DatasetReader {
	private static final String DATASET_FILE_PATH = "dataset/index_file_15000";
	
	public static HashMap<String, HashMap<String, Double>> readDataset() throws IOException{
		HashMap<String, HashMap<String, Double>> datasetMap = new HashMap<String, HashMap<String, Double>>();
		BufferedReader br = new BufferedReader(new FileReader(DATASET_FILE_PATH));
		String line = br.readLine();
		while(line != null){
			String[] spaceSplitStrings = line.split(" ");
			String docId = spaceSplitStrings[1];
			String wordId = spaceSplitStrings[0];
			Double weight = Double.valueOf(spaceSplitStrings[2]);
			if(datasetMap.get(docId) != null){
				datasetMap.get(docId).put(wordId, weight);
			}
			else{
				HashMap<String, Double> wordWeightMap = new HashMap<String, Double>();
				wordWeightMap.put(wordId, weight);
				datasetMap.put(docId,wordWeightMap);
			}
			line = br.readLine();
		}
		br.close();
		return datasetMap;
	}
}

package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import similirity.SimilarityCauculator;
import dataset.DatasetReader;

public class App {
	private final String OUTPUT_PATH_PREFIX = "output/";
	private HashMap<String , HashMap<String, Double>> datasetMap;
	private StringBuilder stringBuilder;
	
	public App(){
		this.datasetMap = new HashMap<String, HashMap<String,Double>>();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		App app = new App();
		System.out.println("Start Initialization");
		app.initialize();
		System.out.println("Initialization finished");
		System.out.println("Output pairs");
		app.outputAllPairs();
		System.out.println("All done");
	}
	
	private void initialize() throws IOException{
		datasetMap = DatasetReader.readDataset();
	}
	
	private void outputResult(String docId1, ArrayList<CandidateDoc> candidateDocsList1, ArrayList<CandidateDoc> candidateDocsList2,
			ArrayList<CandidateDoc> candidateDocsList3) throws IOException{
		ArrayList<ArrayList<CandidateDoc>> candidateDocsListList = new ArrayList<ArrayList<CandidateDoc>>();
		candidateDocsListList.add(candidateDocsList1);
		candidateDocsListList.add(candidateDocsList2);
		candidateDocsListList.add(candidateDocsList3);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				ArrayList<CandidateDoc> candidateDocList = candidateDocsListList.get(i);
				if(j < candidateDocList.size()){
					CandidateDoc candidateDoc = candidateDocList.get(j);
					String docId2 = candidateDoc.getdocId();
					double score2 = candidateDoc.getScore();
					if(score2 != 0.0){
						stringBuilder.append(docId1 + "," + docId2);
						for(int k = 0; k < 3; k++){
							CandidateDoc candidateDoc2 = candidateDocsListList.get(k).get(j);
							double tmpScore = candidateDoc2.getScore();
							stringBuilder.append("," + tmpScore);
						}
						stringBuilder.append("\n");
					}
				}
			}
		}
	}
	
	private void outputAllPairs() throws IOException{
		stringBuilder = new StringBuilder();
		Set<String> datasetKeySet = datasetMap.keySet();
		for(String docId1: datasetKeySet){
			ArrayList<CandidateDoc> candidateDocsList1 = new ArrayList<CandidateDoc>();
			ArrayList<CandidateDoc> candidateDocsList2 = new ArrayList<CandidateDoc>();
			ArrayList<CandidateDoc> candidateDocsList3 = new ArrayList<CandidateDoc>();
			for(String docId2: datasetKeySet){
				if(docId2.compareTo(docId1) > 0){
//					System.out.println("Deal with " + docId1 + " " + docId2);					
					double score1, score2, score3;
					double[] allScore = SimilarityCauculator.cauculateSimilarity(datasetMap.get(docId1), datasetMap.get(docId2));
					score1 = allScore[0];
					score2 = allScore[1];
					score3 = allScore[2];
					candidateDocsList1.add(new CandidateDoc(docId2, score1));
					candidateDocsList2.add(new CandidateDoc(docId2, score2));
					candidateDocsList3.add(new CandidateDoc(docId2, score3));
				}	
			}
			Collections.sort(candidateDocsList1, new CandidateDocComparator());
			Collections.sort(candidateDocsList2, new CandidateDocComparator());
			Collections.sort(candidateDocsList3, new CandidateDocComparator());
			outputResult(docId1, candidateDocsList1, candidateDocsList2, candidateDocsList3);
		}
		File outputFile = new File(OUTPUT_PATH_PREFIX + "similarity_matrix_15000");
		outputFile.getParentFile().mkdirs();
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		bw.write(stringBuilder.toString());
		bw.flush();
		bw.close();
	}
	
	private class CandidateDocComparator implements Comparator<CandidateDoc>{
		@Override
		public int compare(CandidateDoc o1, CandidateDoc o2) {
			return -o1.getScore().compareTo(o2.getScore());
			}	
	}
}
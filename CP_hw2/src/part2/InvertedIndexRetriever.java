package part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class InvertedIndexRetriever {
	private static final String INDEX_FILE_PATH = "hdfs://Quanta006:8100/user/103062510/caOutput/part-00000";
	private static final String INPUT_FILE_PREFIX = "hdfs://Quanta006:8100/user/103062510/caInput/";
	private static final int NUM_SHOW_RESULT = 10;
	private static final int MOVE_OFFSET = 5;
	private static ArrayList<String> searchWordList;
	private static ArrayList<String> searchResultList;
	
	public static void main(String[] args) throws IOException{
		searchWordList = new ArrayList<String>();
		searchResultList = new ArrayList<String>();
		for(String arg: args)
			searchWordList.add(arg.toUpperCase());
		BufferedReader br = retrieveFile(INDEX_FILE_PATH);
		String line=br.readLine();
        while (line != null){
        	line = line.replaceAll("\t", " ");
//        	System.out.println(line);
        	String[] splitString = line.split(" ");
        	String word = splitString[0];
        	if(searchWordList.contains(word)){
        		searchResultList.add(line);
//        		System.out.println("add " + word);
        	}
            line=br.readLine();
        }
        for(String searchResult: searchResultList){ //searchResult: word df fileNmae/tf/offsets;fileName2.....
        	String[] resultSplitString = searchResult.split(" ");
        	String word = resultSplitString[0];
        	String dfString = resultSplitString[1];
        	String searchResultFileListString = resultSplitString[2]; //searchResult: fileNmae/tf/offsets;fileName2.....
        	String[] searchResultFileListStringSplit = searchResultFileListString.split(";");
        	ArrayList<SearchResultObject> searchResultList = new ArrayList<SearchResultObject>();
        	for(String searchResultFileString: searchResultFileListStringSplit){
        		String[] component = searchResultFileString.split("/");
        		searchResultList.add(new SearchResultObject(component[0], dfString, component[1], component[2]));
        	}
        	Collections.sort(searchResultList, new SearchResultObjectComparator());
        	outputResult(word, searchResultList);
        }
	}
	
	private static BufferedReader retrieveFile(String filePath) throws IOException{
		Path pt;
		FileSystem fs;
		BufferedReader br;
        pt = new Path(filePath);
        fs = FileSystem.get(new Configuration());
        br = new BufferedReader(new InputStreamReader(fs.open(pt)));
        return br;
	}
	
	private static void outputResult(String word, ArrayList<SearchResultObject> searchResultList) throws IOException{
		BufferedReader br;
		int numShowResult = (searchResultList.size() >= NUM_SHOW_RESULT)? 10 : searchResultList.size();
		System.out.println("Search " + word);
		for(int i = 0; i < numShowResult; i++){
			SearchResultObject searchResultObject = searchResultList.get(i);
			String fileNameString = searchResultObject.getFileName();
			long firstOffset = searchResultObject.getOffset(0);
			System.out.println("File " + (i+1) + ":" + fileNameString);
			br = retrieveFile(INPUT_FILE_PREFIX + fileNameString);
			int value = 0;
			int charShown = 0, charToShow = word.length() + 2*MOVE_OFFSET;
			if(firstOffset - MOVE_OFFSET <= 0){
				br.skip(firstOffset);
				System.out.println("\t");
			}
			else{
				br.skip(firstOffset - MOVE_OFFSET);
				System.out.print("\t...");
			}
	        while((value = br.read()) != -1 && value != 10)
	        {
	        	char c = (char)value;
	            System.out.print(c);
	            charShown++;
	            if(charShown == charToShow)
	            	break;
	        }
	        System.out.print("...");
	        System.out.println();
		}
	}
}

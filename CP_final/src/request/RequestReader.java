package request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class RequestReader {
	private static final String INPUT_PATH = "input/"; 
	
	public static Request readRequests() throws IOException{
		String[] newFilePathList = getFilePahtList(INPUT_PATH);
		if(newFilePathList != null){
			Random random = new Random();
			int randIndex = 0;
			if(newFilePathList.length > 1)
				randIndex = random.nextInt(newFilePathList.length-1);
			File requestFile = new File(INPUT_PATH + newFilePathList[randIndex]);
			System.out.println(newFilePathList[randIndex]);
			BufferedReader br = new BufferedReader(new FileReader(requestFile));
			String requestString = br.readLine();
			br.close();
			requestFile.delete();
			return new Request(newFilePathList[randIndex], requestString);
		}
		else{
			return null;
		}
	}
	
	private static String[] getFilePahtList(String folderPath){
	    StringBuilder fileList = new StringBuilder();
	    try{
	    	java.io.File folder = new java.io.File(folderPath);
	        String[] list = folder.list();
	        for(int i = 0; i < list.length; i++){
		        if(!list[i].equals(".DS_Store"))
		        	fileList.append(list[i]).append(",");
		    }
	        fileList.deleteCharAt(fileList.length()-1);
	    }catch(Exception e){
	        //System.out.println("'" + folderPath + "'此資料夾不存在");
	        return null;
	    }
	   	return fileList.toString().split(",");
	}
}

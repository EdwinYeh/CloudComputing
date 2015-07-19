package app;

public class CandidateDoc{
	private String docId;
	private Double score;
	
	public CandidateDoc(String docId, double score){
		this.docId = docId;
		this.score = score;
	}
	
	public String getdocId(){
		return this.docId;
	}
	
	public Double getScore(){
		return this.score;
	}

}

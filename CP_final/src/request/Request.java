package request;

public class Request {
	private String requestName;
	private String docId;
	private String[] algoVotes;
	
	public Request(String requestName, String voteString){
		this.requestName = requestName;
		String[] commaSplitStrings = voteString.split(",");
		docId = commaSplitStrings[0];
		algoVotes = new String[commaSplitStrings.length - 1];
		for(int i = 0; i < algoVotes.length; i++){
			algoVotes[i] = commaSplitStrings[i + 1];
		}
	}
	
	public String getRequestName(){
		return this.requestName;
	}
	
	public String getDocId(){
		return this.docId;
	}
	
	public double getAlgoWeight(int i){
		double sum = 0.0;
		for(int j = 0; j < algoVotes.length; j++)
			sum += Double.valueOf(algoVotes[j]);
		return Double.valueOf(algoVotes[i])/ sum;
	}
}

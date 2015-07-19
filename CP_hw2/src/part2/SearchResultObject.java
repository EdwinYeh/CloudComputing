package part2;

public class SearchResultObject {
	private static final double numTotalDocument = 44.0;
	private String fileName;
	private String df;
	private String tf;
	private String[] offsets;
	private double wordWeight;
	
	public SearchResultObject(String fileName, String df, String tf, String offsetString){
		this.fileName = fileName;
		this.df = df;
		this.tf = tf;
		this.offsets = offsetString.split(",");
		this.wordWeight = this.getTf() * Math.log(numTotalDocument/getDf());
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public double getDf(){
		return Double.valueOf(this.df);
	}
	
	public double getTf(){
		return Double.valueOf(this.tf);
	}
	
	public int offsetLength(){
		return Integer.valueOf(this.tf);
	}
	
	public long getOffset(int i){
		return Long.valueOf(offsets[i]);
	}
	
	public double getWeight(){
		return this.wordWeight;
	}
}

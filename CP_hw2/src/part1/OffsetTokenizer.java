package part1;

import java.util.StringTokenizer;

public class OffsetTokenizer extends StringTokenizer{

	private long startOffset;
	private int preTokenLength;
	
	public OffsetTokenizer(String str, String splitBy, long startOffset) {
		super(str, splitBy);
		this.startOffset = startOffset;
		this.preTokenLength = 0;
	}

	@Override
	public String nextToken(){
		startOffset += preTokenLength;
		String nextToken = super.nextToken();
		preTokenLength = nextToken.length() + 1;
		return nextToken;
	}
	
	public String getOffset(){
		return Long.valueOf(startOffset).toString();
	}
}

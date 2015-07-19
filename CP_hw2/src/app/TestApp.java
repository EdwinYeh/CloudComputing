package app;

import part1.OffsetTokenizer;

public class TestApp {
	public static void main(String[] args){	
		String testString = "I love dog.\n I love cat.";
		testString.replaceAll("[^a-zA-Z]+", " ");
		OffsetTokenizer tokenizer = new OffsetTokenizer(testString, " ", 0);
		while(tokenizer.hasMoreTokens()){
			System.out.println(tokenizer.nextToken() + " " + tokenizer.getOffset());
		}
		/*
		MapperOutputValueObject o1 = new MapperOutputValueObject("abc", "1");
		o1.addOffset("2");
		System.out.println(o1);
		*/
	}
}

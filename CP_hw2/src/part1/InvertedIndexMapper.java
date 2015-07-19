package part1;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class InvertedIndexMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, Text, MapperOutputValueObject>{

	@Override
	public void map(LongWritable startOffset, Text value,
			OutputCollector<Text, MapperOutputValueObject> output, Reporter reporter)
			throws IOException {
		//get fileName
		FileSplit fileSplit = (FileSplit) reporter.getInputSplit();
		String fileName = fileSplit.getPath().getName();
		//every word is converted to upper-case
		String splitString = " ";
		OffsetTokenizer tokenizer = new OffsetTokenizer(value.toString().toUpperCase(), splitString, startOffset.get());
		while(tokenizer.hasMoreTokens()){
			String word = tokenizer.nextToken();
			word = word.replaceAll( "[^a-zA-Z]+" , "");
			if(!word.equals("")){
				MapperOutputValueObject outputValue = new MapperOutputValueObject(fileName, tokenizer.getOffset());
				output.collect(new Text(word), outputValue);
			}
		}
	}
}

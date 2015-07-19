package part2;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class CalculateAverageCombiner extends MapReduceBase
	implements Reducer<Text, SumCountPair, Text, SumCountPair> {
		
	public void reduce(Text key, Iterator<SumCountPair> values,
		OutputCollector<Text, SumCountPair> output, Reporter reporter) throws IOException {	
		// TODO : Calculate (key, <SumCountPair1, SumCountPair2 ... >) 
		//		  to (key, SumCountPair1 + SumCountPair2 + ...)
		int totalSum = 0;
		int totalCount = 0;
		while(values.hasNext()){
			SumCountPair tmp = values.next();
			totalSum += tmp.getSum();
			totalCount += tmp.getCount();
		}
		output.collect(key, new SumCountPair(totalSum, totalCount));
		// output.collect(key, /* a SunCountPair instance */);
	}
}

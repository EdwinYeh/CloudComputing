package part1;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class InvertedIndexReducer  extends MapReduceBase
	implements Reducer<Text, MapperOutputValueObject, Text, ReducerOutputValueObject>{

	@Override
	public void reduce(Text key, Iterator<MapperOutputValueObject> values,
			OutputCollector<Text, ReducerOutputValueObject> output, Reporter reporter)
			throws IOException {
		output.collect(key, new ReducerOutputValueObject(values));
	}

}

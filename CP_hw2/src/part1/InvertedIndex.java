package part1;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class InvertedIndex {
	public static void main(String[] args) throws IOException{
		InvertedIndex invertedIndex = new InvertedIndex();
		invertedIndex.run(args[0], args[1]);
	}
	
	public void run(String inputPath, String outputPath) throws IOException{
		JobConf conf = new JobConf(InvertedIndex.class);
		conf.setJobName("103062510_hw2");
		
		conf.setMapperClass(InvertedIndexMapper.class);
		conf.setReducerClass(InvertedIndexReducer.class);
		conf.setCombinerClass(InvertedIndexCombiner.class);
		
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(MapperOutputValueObject.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(ReducerOutputValueObject.class);
		
		FileInputFormat.addInputPath(conf, new Path(inputPath));
		FileOutputFormat.setOutputPath(conf, new Path(outputPath));

		conf.setNumMapTasks(1);
		conf.setNumReduceTasks(1);
		
		JobClient.runJob(conf);
	}
}

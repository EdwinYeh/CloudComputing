package part1;

import java.lang.Character;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class WordCountPartitioner implements Partitioner<Text, IntWritable> {
	
	public void configure(JobConf job) {
	}

	public int getPartition(Text key, IntWritable value, int numPartitions) {
		char firstCh = key.toString().charAt(0);
		
		if (Character.isUpperCase(firstCh)) {
			if (firstCh <= 'G')
				return 0;
			return 1;
		}
		
		if (firstCh <= 'g')
			return 0;
		return 1;
	}
}

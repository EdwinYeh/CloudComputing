package part1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordCountGroupComparator extends WritableComparator {

	public WordCountGroupComparator() {
		super(Text.class, true);
	}	

	// TODO group by start letter
	public int compare(WritableComparable o1, WritableComparable o2) {
		Text key1 = (Text) o1;
		Text key2 = (Text) o2;
		char ch1 = key1.toString().charAt(0);
		char ch2 = key2.toString().charAt(0);
		
		return ch1 - ch2;
	}
}

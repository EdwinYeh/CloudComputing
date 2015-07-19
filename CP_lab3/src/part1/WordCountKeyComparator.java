package part1;

import java.lang.Character;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordCountKeyComparator extends WritableComparator {
	public WordCountKeyComparator() {
		super(Text.class, true);
	}

	// TODO Order by A -> a -> B -> b .... 
	public int compare(WritableComparable o1, WritableComparable o2) {
		Text key1 = (Text) o1;
		Text key2 = (Text) o2;
		char ch1 = key1.toString().charAt(0);
		char ch2 = key2.toString().charAt(0);
		boolean isUpper1 = Character.isUpperCase(ch1);
		boolean isUpper2 = Character.isUpperCase(ch2);
		
		if (isUpper1 == isUpper2) {
			return ch1 - ch2;
		} else {
			ch1 = Character.toLowerCase(ch1);
			ch2 = Character.toLowerCase(ch2);
			
			if (ch1 < ch2)
				return -1;
			else if (ch1 > ch2)
				return 1;
			else if (isUpper1)
				return -1;
		}
		
		return 1;
	}
}

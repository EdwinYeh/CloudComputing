package part1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class InvertedIndexCombiner extends MapReduceBase
	implements Reducer<Text, MapperOutputValueObject, Text, MapperOutputValueObject> {

	@Override
	public void reduce(Text key, Iterator<MapperOutputValueObject> values,
			OutputCollector<Text, MapperOutputValueObject> output, Reporter reporter)
			throws IOException {
		Map<String, MapperOutputValueObject> fileNameObjectMap
			= new HashMap<String, MapperOutputValueObject>();
		while(values.hasNext()){
			MapperOutputValueObject valueObject = values.next();
			String fileName = valueObject.getFileName();
			//First time see the fileName. Put into the Map.
			if((fileNameObjectMap.get(fileName)) == null){
				fileNameObjectMap.put(fileName, new MapperOutputValueObject(fileName, valueObject.getOffsets()));
			}
			//Not first time. Only append the offset to the previous Object.
			else{
				MapperOutputValueObject oldObject = fileNameObjectMap.get(fileName);
				oldObject.addOffset(valueObject);
				fileNameObjectMap.put(fileName, oldObject);
			}
		}
		Set<String> mapKeySet = fileNameObjectMap.keySet();
		for(String mapkey: mapKeySet)
			output.collect(key, fileNameObjectMap.get(mapkey));
	}

}

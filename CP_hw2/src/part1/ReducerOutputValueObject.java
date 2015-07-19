package part1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Writable;

public class ReducerOutputValueObject implements Writable{
	private long df;
	private String tfOffsetString;
	
	public ReducerOutputValueObject(){
		df = 0L;
		tfOffsetString = new String();
	}
	
	public ReducerOutputValueObject(Iterator<MapperOutputValueObject> values){
		df = 0L;
		List<MapperOutputValueObject> sortList = new ArrayList<MapperOutputValueObject>();
		while(values.hasNext()){
			MapperOutputValueObject object = values.next();
			sortList.add(new MapperOutputValueObject(object.getFileName(), object.getOffsets(), object.getTf()));
			df++;
		}
		Collections.sort(sortList, new MapperOutputValueObjectComparator());
		StringBuilder builder = new StringBuilder();
		builder.append(df + " ");
		for(MapperOutputValueObject o: sortList){
			builder.append(o.toString());
			builder.append(";");
		}
		tfOffsetString = builder.toString();
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		df = in.readLong();
		tfOffsetString = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(df);
		out.writeUTF(tfOffsetString);	
	}
	
	@Override
	public String toString(){
		return tfOffsetString;
	}
	
	private class MapperOutputValueObjectComparator implements Comparator<MapperOutputValueObject>{

		@Override
		public int compare(MapperOutputValueObject o1,
				MapperOutputValueObject o2) {
			return o1.getFileName().compareTo(o2.getFileName());
		}
		
	}
}

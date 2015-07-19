package part1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class MapperOutputValueObject implements Writable{

	private String fileName;
	private long tf;
	private String offsetString;
	
	public MapperOutputValueObject(){
		this.fileName = new String();
		this.offsetString = new String();
		this.tf = 1L;
	}
	
	public MapperOutputValueObject(String fileName, String offsetString){
		this.fileName = fileName;
		this.offsetString = offsetString;
		this.tf = 1L;
	}
	
	public MapperOutputValueObject(String fileName, String offsetString, long tf){
		this.fileName = fileName;
		this.offsetString = offsetString;
		this.tf = tf;
	}
	
	public String getFileName(){
		return fileName.toString();
	}
	
	public long getTf(){
		return tf;
	}
	
	public String getOffsets(){
		return offsetString;
	}
	
	public void addOffset(MapperOutputValueObject addObject){
		String newOffsetString = addObject.getOffsets();
		long newTf = addObject.getTf();
		this.offsetString = this.offsetString + "," + newOffsetString;
		this.tf += newTf;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		fileName = in.readUTF();
		tf = in.readLong();
		offsetString = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(fileName);
		out.writeLong(tf);
		out.writeUTF(offsetString);
	}

	@Override
	public String toString(){
		return fileName + "/" + tf + "/" + offsetString;
	}
}

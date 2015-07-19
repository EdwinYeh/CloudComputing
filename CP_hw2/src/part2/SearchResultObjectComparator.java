package part2;

import java.util.Comparator;

public class SearchResultObjectComparator implements Comparator<SearchResultObject>{

	@Override
	public int compare(SearchResultObject o1, SearchResultObject o2) {
		return (o1.getWeight() < o2.getWeight())?1:-1;
	}
	
}
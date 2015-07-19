package similirity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SimilarityCauculator {
//	final static int USE_COS = 1; //the higher the similarer
//	final static int USE_AE = 2; //the lower the similar
//	final static int USE_JACCARD = 3; //the higher the similarer

	public static double[] cauculateSimilarity(HashMap<String, Double> docMap1,
			HashMap<String, Double> docMap2) {
		Set<String> mapKeySet1 = null,mapKeySet2 = null;
		try{
			mapKeySet1 = docMap1.keySet();
			mapKeySet2 = docMap2.keySet();
		} catch(NullPointerException e){
			System.out.println("No words");
			double[] allScore = new double[3];
			return allScore;
		}
		Set<String> mapKeySet = new HashSet<String>();
		mapKeySet.addAll(mapKeySet1);
		mapKeySet.addAll(mapKeySet2);
		Double cosScore = 0.0, aeScore = 0.0, jaccardScore = 0.0;
		Double cosNorm1 = 0.0, cosNorm2 = 0.0;
		Double jaccardChild = 0.0, jaccardMother = 0.0;
		for (String key: mapKeySet) {
			Double weight1 = docMap1.get(key);
			Double weight2 = docMap2.get(key);
			Double val1 = 0.0, val2 = 0.0;
			if(weight1 != null){
				cosNorm1 += weight1* weight1;
				val1 = weight1;
			}
			if(weight2 != null){
				cosNorm2 += weight2* weight2;
				val2 = weight2;
			}
			if((weight1 != null) || (weight2 != null))
				jaccardMother += 1.0;
			if((weight1 != null) && (weight2 != null)){
				cosScore += weight1 * weight2;
				jaccardChild += 1.0;
			}
			aeScore += Math.abs(val1 - val2);
		}
		cosNorm1 = Math.pow(cosNorm1, 0.5);
		cosNorm2 = Math.pow(cosNorm2, 0.5);
		cosScore = cosScore/ (cosNorm1* cosNorm2);
		aeScore = 1/ aeScore;
		if(aeScore > 1)
			aeScore = 1.0;
		jaccardScore = jaccardChild/ jaccardMother;
		double[] allScore = new double[3];
		allScore[0] = cosScore; allScore[1] = aeScore; allScore[2] = jaccardScore;
		return allScore;
	}

}

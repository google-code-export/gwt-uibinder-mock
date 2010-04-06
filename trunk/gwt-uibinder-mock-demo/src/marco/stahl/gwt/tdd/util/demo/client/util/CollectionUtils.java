package marco.stahl.gwt.tdd.util.demo.client.util;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionUtils {
	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static ArrayList<Integer> integerRange(int min,int max) {
		ArrayList<Integer> beerValues = newArrayList();
		for (int i = min; i <= max; i++) {
			beerValues.add(i);
		}
		return beerValues;
	}
	
	
}

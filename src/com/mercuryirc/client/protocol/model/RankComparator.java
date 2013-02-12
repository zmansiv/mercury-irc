package com.mercuryirc.client.protocol.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RankComparator implements Comparator<String> {
	private static final Map<Character, Integer> priority = new HashMap<Character, Integer>();

	static {
		priority.put('+', 1);
		priority.put('%', 2);
		priority.put('@', 3);
		priority.put('&', 4);
		priority.put('~', 5);
	}

	public int compare(String o1, String o2) {
		if(!isRank(o1.charAt(0)) && !isRank(o2.charAt(0)))
			return o1.compareTo(o2);

		else {
			// WHY IS AUTOBOXING A THING
			// WHY DO I HAVE TO PUT Integers INTO HASHMAPS
			Integer p1m = priority.get(o1.charAt(0));
			Integer p2m = priority.get(o2.charAt(0));

			// if p1m isn't null, then there was an entry for it
			// in the mapping (the user had a rank), otherwise
			// the user didn't have a rank and set priority to 0
			int p1 = p1m != null ? p1m : 0;
			int p2 = p2m != null ? p2m : 0;

			if(p1 < p2)
				return -1;
			else if(p1 > p2)
				return 1;
			else {
				// same rank, do alphabetical
				o1 = o1.substring(1);
				o2 = o2.substring(1);

				return o1.compareTo(o2);
			}
		}
	}

	public static boolean isRank(char ch) {
		return ch == '+' || ch == '%' || ch == '@' || ch == '&' || ch == '~';
	}
}

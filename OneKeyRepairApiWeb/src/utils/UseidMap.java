package utils;

import java.util.HashMap;
import java.util.Map;

public class UseidMap {
	private static Map<String, String> useidMap= new HashMap<String, String>();
	public static void put(String useid, String phone){
		useidMap.put(useid, phone);
	}
	public static String get(String useid){
		return useidMap.get(useid);
	}
}

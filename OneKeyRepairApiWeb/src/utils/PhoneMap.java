package utils;

import java.util.HashMap;
import java.util.Map;

public class PhoneMap {
	private static Map<String, String> phoneMap= new HashMap<String, String>();
	public static void put(String phone, String receive){
		phoneMap.put(phone, receive);
	}
	public static String get(String phone){
		return phoneMap.get(phone);
	}
}

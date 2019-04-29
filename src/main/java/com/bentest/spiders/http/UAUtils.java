package com.bentest.spiders.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UAUtils {
	private static List<String> uaList = new ArrayList<>();

	/**
	 * 获取随机的UA
	 * @return
	 */
	public static String getRandomUA() {
		if(uaList == null || uaList.size() < 1) {
			return null;
		}
		Random random = new Random();
		int n = random.nextInt(uaList.size());
		return uaList.get(n);
	}
	
	public static List<String> getUaList() {
		return uaList;
	}

	public static void setUaList(List<String> uaList) {
		UAUtils.uaList = uaList;
	}
	
	
}

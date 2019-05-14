package com.bentest.spiders.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bentest.spiders.entity.AmzUA;

public class UAUtils {
	private static List<AmzUA> uaList = new ArrayList<>();

	/**
	 * 获取随机的UA
	 * @return
	 */
	public static AmzUA getRandomUA() {
		if(uaList == null || uaList.size() < 1) {
			return null;
		}
		Random random = new Random();
		int n = random.nextInt(uaList.size());
		return uaList.get(n);
	}
	
	public static List<AmzUA> getUaList() {
		return uaList;
	}

	public static void setUaList(List<AmzUA> uaList) {
		UAUtils.uaList = uaList;
	}
	
	
}

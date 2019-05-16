package com.qf.utils;

public class CreateNumber {
	private static String info = "";
	public static String createNumber() {
		for (int i = 0; i < 4; i++) {
			if (i % 2 ==0) {
				int a = (int) (Math.random()*10);
				info+=a;
			}
			else if (i % 2 != 0) {
				char c=(char)(int)(Math.random()*26+97);
				info+=c;
			}
		}
		return info.trim();
	}
}

package com.cemk.exp.date.util;

public class DAOUtil {

	public static String getTime(){
		
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		return currentTime;
	}
}

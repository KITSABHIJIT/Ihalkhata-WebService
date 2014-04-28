package com.exp.cemk.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static long getTimeDiffFromCurrentTime(int hour, int min) {
		Calendar calCurrent = Calendar.getInstance();
		Calendar calDesired = Calendar.getInstance();
		long millisecondsCurrent = 0;
		long millisecondsDesired = 0;
		long diff = 0;
		calDesired.set(calCurrent.get(Calendar.YEAR),
				calCurrent.get(Calendar.MONTH), calCurrent.get(Calendar.DATE),
				hour, min, 0);
		millisecondsCurrent = calCurrent.getTimeInMillis();
		millisecondsDesired = calDesired.getTimeInMillis();

		if (millisecondsDesired < millisecondsCurrent) {
			calDesired.set(calCurrent.get(Calendar.YEAR),
					calCurrent.get(Calendar.MONTH),
					calCurrent.get(Calendar.DATE) + 1, hour, min, 0);
			diff = calDesired.getTimeInMillis() - millisecondsCurrent;
		} else {
			diff = millisecondsDesired - millisecondsCurrent;
		}
		return diff;

	}

	public static String getHourMinSecond(long milliseconds) {
		long millSeconds = (long) (milliseconds) % 1000;
		long seconds = (long) (milliseconds / 1000) % 60;
		long minutes = (long) ((milliseconds / (1000 * 60)) % 60);
		long hours = (long) ((milliseconds / (1000 * 60 * 60)) % 24);
		return hours + " hrs " + minutes + " mins " + seconds + " sec "
				+ millSeconds + " millis";
	}

	public static String getFormatedCurrentDate(String format) {
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date (month/day/year)
		// DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		DateFormat df = new SimpleDateFormat(format);
		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(today);

		// Print what date is today!
		// System.out.println("Report Date: " + reportDate);
		return reportDate;
	}

	public static String getFormatedDate(String desiredformat,
			String inputformat, String date) {
		try {
			Date dateObj = new SimpleDateFormat(inputformat).parse(date);
			String formattedDate = new SimpleDateFormat(desiredformat)
					.format(dateObj);
			return formattedDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static String getStartMonth(String selectionType) {
		int result = 0;
		Calendar calCurrent = Calendar.getInstance();
		int month = calCurrent.get(Calendar.MONTH) + 1;
		if (selectionType.equals("0"))
			result = month;
		else if (selectionType.equals("1")) {
			result = (month == 1) ? 12 : (month - 1);
		} else if (selectionType.equals("2")) {
			if (month == 1)
				result = 11;
			else if (month == 2)
				result = 12;
			else
				result = month - 2;

		} else if (selectionType.equals("3"))
			result = month;
		return Integer.toString(result);
	}

	public static String getEndMonth(String selectionType) {
		int result = 0;
		Calendar calCurrent = Calendar.getInstance();
		int month = calCurrent.get(Calendar.MONTH) + 1;
		if (selectionType.equals("0"))
			result = month;
		else if (selectionType.equals("1")) {
			result = (month == 1) ? 12 : (month - 1);
		} else if (selectionType.equals("2"))
			result = month;
		else if (selectionType.equals("3"))
			result = (month == 1) ? 12 : (month - 1);
		return Integer.toString(result);

	}

	public static String getStartYear(String selectionType) {
		int result = 0;
		Calendar calCurrent = Calendar.getInstance();
		int month = calCurrent.get(Calendar.MONTH);
		int year = calCurrent.get(Calendar.YEAR);
		if (selectionType.equals("0"))
			result = year;
		else if (selectionType.equals("1")) {
			result = (month == 0) ? (year - 1) : year;
		} else if (selectionType.equals("2")) {
			if (month == 0)
				result = (year - 1);
			else if (month == 1)
				result = (year - 1);
			else
				result = year;
		} else if (selectionType.equals("3"))
			result = (year - 1);
		return Integer.toString(result);

	}

	public static String getEndYear(String selectionType) {
		int result = 0;
		Calendar calCurrent = Calendar.getInstance();
		int month = calCurrent.get(Calendar.MONTH);
		int year = calCurrent.get(Calendar.YEAR);
		if (selectionType.equals("0"))
			result = year;
		else if (selectionType.equals("1")) {
			result = (month == 0) ? (year - 1) : year;
		} else if (selectionType.equals("2"))
			result = year;
		else if (selectionType.equals("3"))
			result = (month == 0) ? (year - 1) : year;
		return Integer.toString(result);

	}

}

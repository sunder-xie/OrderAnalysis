package org.sysu.sdcs.order.analysis.utils.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;

public class DateUtil {
	private static final String LINE_PATTERN = "yyyy-MM-dd";
	private static final String COMPLETE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat();

	public static String format(Date date) {
		if (date == null) {
			return Strings.EMPTY;
		}
		FORMATTER.applyPattern(LINE_PATTERN);
		String dateMark = FORMATTER.format(date);
		return dateMark;
	}

	public static String completeFormat(Date date) {
		if (date == null) {
			return Strings.EMPTY;
		}
		FORMATTER.applyPattern(COMPLETE_PATTERN);
		String dateMark = FORMATTER.format(date);
		return dateMark;
	}

	public static Date add(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, offset);
		return cal.getTime();
	}

	public static Date getDateStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.MINUTE, 0);
		cal.add(Calendar.SECOND, 0);
		cal.add(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Map<String, Integer> addDateCount(Map<String, Integer> dateGroup, String date) {
		if (dateGroup == null) {
			dateGroup = new HashMap<>();
		}
		Integer count = dateGroup.get(date);
		if (count == null) {
			count = new Integer(1);
		} else {
			count = new Integer(count + 1);
		}
		dateGroup.put(date, count);
		return dateGroup;
	}
}

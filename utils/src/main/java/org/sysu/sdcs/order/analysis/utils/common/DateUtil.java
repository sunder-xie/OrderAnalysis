package org.sysu.sdcs.order.analysis.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.util.Strings;

public class DateUtil {
	private static final String LINE_PATTERN = "yyyy-MM-dd";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat();

	public static String format(Date date) {
		if (date == null) {
			return Strings.EMPTY;
		}
		FORMATTER.applyPattern(LINE_PATTERN);
		String dateMark = FORMATTER.format(date);
		return dateMark;
	}
}

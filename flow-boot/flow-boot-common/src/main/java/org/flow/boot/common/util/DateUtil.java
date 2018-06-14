package org.flow.boot.common.util;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DateUtil {

	private static ThreadLocal<Map<String, SoftReference<SimpleDateFormat>>> threadFormatter = new ThreadLocal<>();

	public static SimpleDateFormat getDateFormatter(String pattern) {
		SimpleDateFormat sdf = null;
		Map<String, SoftReference<SimpleDateFormat>> map = threadFormatter.get();
		if (Objects.isNull(map)) {
			sdf = new SimpleDateFormat(pattern);
			map = new HashMap<>();
			map.put(pattern, new SoftReference<SimpleDateFormat>(sdf));
			threadFormatter.set(map);
		} else if (Objects.isNull(map.get(pattern))) {
			sdf = new SimpleDateFormat(pattern);
			map.put(pattern, new SoftReference<SimpleDateFormat>(sdf));
		} else if (Objects.isNull(sdf = map.get(pattern).get())) {
			sdf = new SimpleDateFormat(pattern);
			map.put(pattern, new SoftReference<SimpleDateFormat>(sdf));
		}
		return sdf;
	}

	public static SimpleDateFormat getDateFormatter(String pattern, Locale locale) {
		if (Objects.isNull(locale)) {
			return getDateFormatter(pattern);
		}
		String key = pattern + locale.getLanguage();
		SimpleDateFormat sdf = null;
		Map<String, SoftReference<SimpleDateFormat>> map = threadFormatter.get();
		if (Objects.isNull(map)) {
			sdf = new SimpleDateFormat(pattern, locale);
			map = new HashMap<>();
			map.put(key, new SoftReference<SimpleDateFormat>(sdf));
			threadFormatter.set(map);
		} else if (Objects.isNull(map.get(key))) {
			sdf = new SimpleDateFormat(pattern, locale);
			map.put(key, new SoftReference<SimpleDateFormat>(sdf));
		} else if (Objects.isNull(sdf = map.get(key).get())) {
			sdf = new SimpleDateFormat(pattern, locale);
			map.put(key, new SoftReference<SimpleDateFormat>(sdf));
		}
		return sdf;
	}

	public static Date getDate(String dateValue, String pattern) throws ParseException {
		if (Objects.isNull(dateValue) || dateValue.length() == 0) {
			return null;
		}
		return getDateFormatter(pattern).parse(dateValue);
	}

	public static Date getDate(String dateValue, String pattern, Locale locale) throws ParseException {
		if (Objects.isNull(dateValue) || dateValue.length() == 0) {
			return null;
		}
		return getDateFormatter(pattern, locale).parse(dateValue);
	}

	public static String formatDate(Date date, String pattern) {
		if (Objects.isNull(date)) {
			return "";
		}
		return getDateFormatter(pattern).format(date);
	}

}

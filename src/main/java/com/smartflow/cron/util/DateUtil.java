package com.smartflow.cron.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.smartflow.cron.pojo.TimeOfDay;
/**
 * 计算时间工具类
 * @author smartflow
 *
 */
public class DateUtil {
	
	private static final String SDF_DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String SDF_DATETIME_SHOT = "yyyyMMddHHmmss";
	private static final String SDF_DATETIME_MS = "yyyyMMddHHmmssSSS";
	private static final String SDF_DATE = "yyyy-MM-dd";
	
	/**
	 * 字符串转日期
	 * @param dateStr 日期字符串
	 * @param pattern 格式化字符串
	 * @return 日期
	 */
	public static Date toDate(String dateStr, String pattern) {
		try{
			if(null != pattern && !"".equals(pattern)) {
				return new SimpleDateFormat(pattern).parse(dateStr);
			}else {
				return new SimpleDateFormat(SDF_DATETIME).parse(dateStr);
			}
		}catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 字符串转日期
	 * @param dateStr 日期字符串
	 * @return 日期 yyyy-MM-dd HH:mm:ss
	 */
	public static Date toDate(String dateStr) {
		return toDate(dateStr, null);
	}

	/**
	 * 日期转字符串
	 * @param date 日期
	 * @param format 格式化字符串
	 * @return 字符串
	 */
	public static String toStr(Date date, String format) {
		SimpleDateFormat sdf;
		if(null != format && !"".equals(format)) {
			sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else {
			sdf = new SimpleDateFormat(SDF_DATETIME);
			return sdf.format(date);
		}
		
	}
	/**
	 * 日期转字符串
	 * @param date 日期
	 * @return 字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String toStr(Date date) {
		return toStr(date, SDF_DATETIME);
	}
	
	/**
	 * 计算某一天是一个月的哪一天
	 * @param calendar 日期
	 * @return 1-31
	 */
	public static int day(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 计算某一天是一个月的哪一天
	 * @param date 日期
	 * @return 1-31
	 */
	public static int day(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return day(calendar);
	}
	
	/**
	 * 计算某一天是星期几
	 * @param calendar 日期
	 * @return 星期几 星期天-星期六 0-6
	 */
	public static int week(Calendar calendar) {
		return calendar.get(calendar.DAY_OF_WEEK) -1;//@return 星期几 星期天是0 0-6
//		return calendar.get(calendar.DAY_OF_WEEK);//@return 星期几 星期一是1，星期天是7 （1-7）
	}
	
	/**
	 * 计算某一天是星期几
	 * @param date 日期
	 * @return 星期几 0-6
	 */
	public static int week(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return week(calendar);
	}
	
	/**
	 * 计算某一天的月份
	 * @param calendar 日期
	 * @return 月份 1-12
	 */
	public static int month(Calendar calendar) {
		return calendar.get(calendar.MONTH) + 1;
	}
	
	/**
	 * 计算某一天的月份
	 * @param date 日期
	 * @return 月份 1-12
	 */
	public static int month(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return month(calendar);
	}

	/**
	 * 计算某一天的年
	 * @param calendar 日期
	 * @return 年
	 */
	public static int year(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * 计算某一天的年
	 * @param date 日期
	 * @return 年
	 */
	public static int year(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return year(calendar);
	}
	
	/**
	 * 计算某一天的时
	 * @param calendar 日期
	 * @return 时 0-23
	 */
	public static int hour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 计算某一天的时
	 * @param date 日期
	 * @return 时 0-23
	 */
	public static int hour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return hour(calendar);
	}
	
	/**
	 * 计算某一天的分
	 * @param calendar 日期
	 * @return 分 0-59
	 */
	public static int minute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 计算某一天的分
	 * @param date 日期
	 * @return 分 0-59
	 */
	public static int minute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return minute(calendar);
	}
	
	/**
	 * 计算某一天的秒
	 * @param calendar 日期
	 * @return 秒 0-59
	 */
	public static int second(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 计算某一天的秒
	 * @param date 日期
	 * @return 秒 0-59
	 */
	public static int second(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return second(calendar);
	}
	
	/**
	 * 计算两个时分秒时间的差距是否在给定的容忍范围内
	 * @param one 比较的一方
	 * @param two 比较的另外一方
	 * @param seconds 容忍范围
	 * @return true if in the range of seconds or false if out of the range
	 */
	public static boolean equalsWithTolerance(TimeOfDay one, TimeOfDay two, Integer seconds){
		//秒数为0退化为equals
		if(null == seconds || 0 == seconds){
			return one.equals(two);
		}
		//秒数是否在给定的容忍范围内
		return distance(one, two) <= seconds;
	}
	/**
	 * 判断秒数是否在容忍的范围内
	 * @param one
	 * @param two
	 * @return
	 */
	public static long distance(TimeOfDay one, TimeOfDay two) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.HOUR_OF_DAY, one.getHour());
		calendar1.set(Calendar.MINUTE, one.getMinute());
		calendar1.set(Calendar.SECOND, one.getSecond());
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.HOUR_OF_DAY, two.getHour());
		calendar2.set(Calendar.MINUTE, two.getMinute());
		calendar2.set(Calendar.SECOND, two.getSecond());
		
		//秒数是否在给定的容忍范围内
		return Math.abs(calendar1.getTimeInMillis() / 1000 - calendar2.getTimeInMillis() / 1000);
	}

}

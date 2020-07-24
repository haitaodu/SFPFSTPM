package com.smartflow.cron.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.smartflow.cron.pojo.CronPosition;
/**
 * 辅助工具类 替换？ JAN-DEC、SUN-SAT等字符串的整形工具类
 * @author smartflow
 *
 */
public class CronShapingUtil {

	private static final Map<String, String> MONTH_MAP = new HashMap<String, String>(12);
	private static final Map<String, String> WEEK_MAP = new HashMap<String, String>(7);
	static {
		MONTH_MAP.put("JAN", "1");
		MONTH_MAP.put("FEB", "2");
		MONTH_MAP.put("MAR", "3");
		MONTH_MAP.put("APR", "4");
		MONTH_MAP.put("MAY", "5");
		MONTH_MAP.put("JUN", "6");
		MONTH_MAP.put("JUL", "7");
		MONTH_MAP.put("AUG", "8");
		MONTH_MAP.put("SEP", "9");
		MONTH_MAP.put("OCT", "10");
		MONTH_MAP.put("NOV", "11");
		MONTH_MAP.put("DEC", "12");

//		WEEK_MAP.put("SUN", "1");//星期天作为一周的第一天
//		WEEK_MAP.put("MON", "2");
//		WEEK_MAP.put("TUE", "3");
//		WEEK_MAP.put("WED", "4");
//		WEEK_MAP.put("THU", "5");
//		WEEK_MAP.put("FRI", "6");
//		WEEK_MAP.put("SAT", "7");
	
//		WEEK_MAP.put("MON", "1");
//		WEEK_MAP.put("TUE", "2");
//		WEEK_MAP.put("WED", "3");
//		WEEK_MAP.put("THU", "4");
//		WEEK_MAP.put("FRI", "5");
//		WEEK_MAP.put("SAT", "6");
//		WEEK_MAP.put("SUN", "7");
		

		WEEK_MAP.put("SUN", "0");
		WEEK_MAP.put("MON", "1");
		WEEK_MAP.put("TUE", "2");
		WEEK_MAP.put("WED", "3");
		WEEK_MAP.put("THU", "4");
		WEEK_MAP.put("FRI", "5");
		WEEK_MAP.put("SAT", "6");
	}
	/**
	 * 域整形，把某些英文字符串像JAN/SUN等转换为相应的数字
	 * @param express
	 * @param cronPosition
	 * @return
	 */
	public static String shaping(String express, CronPosition cronPosition) {
		if(cronPosition == CronPosition.MONTH) {
			express = shapingMonth(express);
		}

		if(cronPosition == cronPosition.WEEK) {
			express = shapingWeek(express);
			express = express.replace("?", "*");//* 表示所有值，用在（月）表示每个月，用在（星期）表示星期的每一天   //？仅被用于月和星期，表示不指定值
		}

		if(cronPosition == cronPosition.DAY) {
			express = express.replace("?", "*");
		}
		return express;
	}
	/**
	 * 将月份的大写字母转换成对应的月份数字
	 * @param express
	 * @return
	 */
	private static String shapingMonth(String express) {
		Set<Map.Entry<String, String>> entrySet = MONTH_MAP.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			if(express.toUpperCase().contains(entry.getKey())) {
				express = express.toUpperCase().replace(entry.getKey(), entry.getValue());
			}
		}
		return express;
	}
	/**
	 * 将星期的大写字母转换成对应的星期数字
	 * @param express
	 * @return
	 */
	private static String shapingWeek(String express) {
		Set<Map.Entry<String, String>> entrySet = WEEK_MAP.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			if(express.toUpperCase().contains(entry.getKey())) {
				express = express.toUpperCase().replace(entry.getKey(), entry.getValue());
			}
		}
		return express;
	}
}

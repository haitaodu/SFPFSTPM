package com.smartflow.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronExpression;

import com.smartflow.cron.util.CronUtils;

public class GetDateByCRONExpressionUtil {

	public static List<Date> dateList = new ArrayList<Date>();
	
	public static List<Date> getDateFromCRONExpression(Date startDateTime, Date endDateTime, String cronExpression){
		Date next;
		try {
			if(cronExpression.contains("L") || cronExpression.contains("W") || cronExpression.contains("#")){
				CronExpression expression = new CronExpression(cronExpression);
				next = expression.getNextValidTimeAfter(startDateTime);
			}else {
				next = CronUtils.next(cronExpression, startDateTime);
			}
			if(next.getTime() <= endDateTime.getTime()){
				dateList.add(next);
				getDateFromCRONExpression(next, endDateTime, cronExpression);
			}
			return dateList;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static List<String> parseDatetoString(List<Date> dateList){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateStrList = new ArrayList<>();
		for (Date date : dateList) {
			dateStrList.add(sdf.format(date));
		}
		return dateStrList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			init();
			//0 30 2 1W * ? 每月1号最近的那个工作日执行
			//0 30 2 ? * 1L 每月的最后一个星期日执行
			//0 30 2 LW * ? 每月最后一个工作日
			//0 30 2 ? * 6#3 每月第三个周五
			List<Date> dateList = getDateFromCRONExpression(new Date(),sdf.parse("2019-11-13 17:38:00"), "0 30 2 ? * 6#3");	
			List<String> dateStrList = parseDatetoString(dateList);
			for (String dateStr : dateStrList) {
				System.out.println(dateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Map<String,List<Object>> map = new HashMap<>();
//		List<String> strList = new ArrayList<>();
//		strList.add("2019-08-09");
//		strList.add("2019-08-10");
//		strList.add("2019-08-09");
//		
//		strList.add("2019-08-09");	
//		strList.add("2019-08-09");
//		
//		Integer a = 1;
//		for (String str : strList) {
//			List<Object> list = null;
//			if(map.containsKey(str)){
//				list= map.get(str);
//			}else{
//				list = new ArrayList<>();
//			}
//			list.add(a);
//			map.put(str, list);
//			a++;
//		}
//		System.out.println(map.get("2019-08-09"));
//		System.out.println(map.get("2019-08-10"));
	}
	public static void init()
	{
		dateList = new ArrayList<Date>();
	}

}

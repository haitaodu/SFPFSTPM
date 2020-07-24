package com.smartflow.cron.pojo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.smartflow.cron.util.CompareUtil;
/**
 * cron表达式的域，保存域的相关信息，并提供计算执行点的方法
 * @author smartflow
 *
 */
public class CronField {

	public static final String STAR = "*";//星号    代表所有可能的值
	public static final String COMMA = ",";//逗号 列出枚举(eg 在分钟里 "5,15"表示5分钟和20分钟触发)
	public static final String HYPHEN = "-";//连字符 指定范围
	public static final String SLASH = "/";//斜杠 指定增量 (eg 在分钟里 "3/15"表示从3分钟开始，每隔15分钟执行一次)

//	public static final String L = "L";//用于日和星期字段 last的缩写 星期上用5L表示最后一个星期四触发
//	public static final String W = "W";//用于日字段。用来指定离给定日期最近的工作日（周一到周五）,W的寻找范围不会跨越月份
//	public static final String LW = "LW";//组合用于日字段，表示该月最后一个工作日
//	public static final String POUND = "#";//用于星期字段，表示该月第几个周几，eg.6#3表示该月第三个周五，如果指定#5，则该月不会触发

	private CronPosition cronPosition;
	private String express;
	private List<Integer> listCache = null;

//	public static boolean lastdayOfWeek = false;//每周的最后一天（星期六）  [L用在星期字段上时]
//	public static boolean lastdayOfMonth = false;//每月的最后一天 [L用在日字段上时]
//	public static boolean nearestWeekday = false;//最近的工作日 
//	public static boolean lastWeekdayOfMonth = false;//本月最后一个工作日 [LW用在日字段上时]
//	public static boolean lastweekOfMonth = false;//每月的最后一个星期
//	public static int nthdayOfWeek = 0;//每月第几个周几  [6#2 表示每月第2周的星期五]
//	public static int nthlastweekOfMonth = 0;//每月的最后一个星期几
//	public static int nthdayOfMonthNearestWeekday = 0;//每月几号最近的工作日
	
	public CronField(CronPosition cronPosition, String express){
		this.cronPosition = cronPosition;
		this.express = express;
	}

	public CronPosition getCronPosition() {
		return cronPosition;
	}

	public void setCronPosition(CronPosition cronPosition) {
		this.cronPosition = cronPosition;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	/**
	 * 是否包含全部的数值
	 * @return
	 */
	public boolean containsAll(){
		return STAR.equals(getExpress());
	}

	/**
	 * 计算某域的哪些点
	 * @return
	 */
	public List<Integer> points(){
		//缓存计算的
		if(null != listCache){
			return listCache;
		}
		listCache = new ArrayList<Integer>(5);

		String express = this.getExpress();
		CronPosition cronPosition = this.getCronPosition();
		Integer min = cronPosition.getMin();
		Integer max = cronPosition.getMax();
		// *这种情况
		if (STAR.equals(express)) {
			for (int i = min; i <= max; i++) {
				listCache.add(i);
			}
			return listCache;
		}
		//带有,逗号的情况，分割之后每部分单独处理
		if(express.contains(COMMA)) {
			String[] split = express.split(COMMA);
			for (String part : split) {
				listCache.addAll(new CronField(this.getCronPosition(), part).points());
			}
			if(listCache.size() > 1) {
				//去重
				CompareUtil.removeDuplicate(listCache);
				//排序
				Collections.sort(listCache);
			}
			return listCache;
		}
		// 0-3 0/2 3-15/2 5模式统一为(min-max)/step
		Integer left = null;
		Integer right = null;
		Integer step = 1;

		//包含-的情况
		if(express.contains(HYPHEN)) {
			String[] strings = express.split(HYPHEN);
			left = Integer.valueOf(strings[0]);
			CompareUtil.assertRange(cronPosition, left);//判断值是否在该域范围
			//1-32/2
			if(strings[1].contains(SLASH)) {
				String[] split = strings[1].split(SLASH);
				//32
				right = Integer.valueOf(split[0]);
				CompareUtil.assertSize(left, right);
				CompareUtil.assertRange(cronPosition, right);
				//2
				step = Integer.valueOf(split[1]);
			}else{
				//1-32的情况
				right = Integer.valueOf(strings[1]);
				CompareUtil.assertSize(left, right);
				CompareUtil.assertRange(cronPosition, right);
			}
			//仅仅包含/
		}else if (express.contains(SLASH)){
			String[] strings = express.split(SLASH);
			left = Integer.valueOf(strings[0]);
			CompareUtil.assertRange(cronPosition, left);
			step = Integer.valueOf(strings[1]);
			right = max;
			CompareUtil.assertSize(left, right);
		}
		/**
		else if (express.contains(L)){
			if(express.equals(L)){//表明只有一个L，每月的最后一天
				if(cronPosition.getPosition() == 3){//日
					lastdayOfMonth = true;
					left = 28;
					right = 31;
				}else if(cronPosition.getPosition() == 5){//星期
					lastdayOfWeek = true;
					left = 6;
					right = 6;//每周最后一天（星期六）
				}
			}else if(express.equals(LW)){//表明包含LW
				lastWeekdayOfMonth = true;
				left = 0;
				right = 6;				
			}else{//数字加L 表明这个月的最后星期X  eg.6L表示这个月最后星期五
				lastweekOfMonth = true;
				nthlastweekOfMonth = Integer.parseInt(express.substring(0, express.indexOf(L)));
				if(nthlastweekOfMonth == 1){
					nthlastweekOfMonth = 0;//星期日
				}else{
					nthlastweekOfMonth = nthlastweekOfMonth - 1;
				}
				left = nthlastweekOfMonth;
				right = nthlastweekOfMonth;
			}
		}else if(express.contains(W)){
			if(express.equals(W)){
				nearestWeekday = true;
			}else{//指定每月几号最近的那个工作日
				nthdayOfMonthNearestWeekday = Integer.parseInt(express.substring(0, express.indexOf(W)));
				left = 1;
				right = 31;
			}
		}else if (express.contains(POUND)){
			String[] strings = express.split(POUND);
			left = Integer.valueOf(strings[0]);
			if(left > 4){
				throw new IllegalArgumentException("every month has 4 weeks, but found "+left);
			}else {
				right = Integer.valueOf(strings[1]);
			}
			CompareUtil.assertRange(cronPosition, right);//判断值是否在该域范围
		}**/
		else{
			//普通的数字
			Integer single = Integer.valueOf(express);
			//星期域上7 转换为0
			if(cronPosition.WEEK == this.cronPosition && 7 == single) {
				single = 0;
			}

			//			if(cronPosition.WEEK == this.cronPosition && 7 == single) {
			//				single = 1;
			//			}else{
			//				single = single - 1;	
			//			}
			CompareUtil.assertRange(cronPosition, single);
			listCache.add(single);
			return listCache;
		}

		for (int i = left; i <= right; i += step) {
			listCache.add(i);
		}
		return listCache;
	}

	@Override
	public String toString() {
		return "CronField [cronPosition=" + cronPosition + ", express=" + express + "]";
	}

	/**
	 * 判断是否是闰年
	 * @param year 年份
	 * @return
	 */
	public boolean isLeapYear(int year) {
		return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
	}
	/**
	 * 获取每月的最后一天
	 * @param monthNum 月份
	 * @param year 年份
	 * @return
	 */
	public int getLastDayOfMonth(int monthNum, int year) {

		switch (monthNum) {
		case 1:
			return 31;
		case 2:
			return (isLeapYear(year)) ? 29 : 28;
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		default:
			throw new IllegalArgumentException("Illegal month number: "
					+ monthNum);
		}
	}


}

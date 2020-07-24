package com.smartflow.cron.pojo;
/**
 * cron表达式某个位置上的一些常量，可以指定位置参数、最小值、最大值
 * @author smartflow
 *
 */
public enum CronPosition {
	SECOND(0 , 0 , 59) ,//秒 取值（0-59）
    MINUTE(1 , 0 , 59) ,//分钟 取值（0-59）
    HOUR  (2 , 0 , 23) ,//小时  取值（0-59）
    DAY   (3 , 1 , 31) ,//DayOfMonth 日期   取值（1-31）
    MONTH (4 , 1 , 12) ,//月份 取值（1-12）或者（JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC）
    //WEEK  (5 , 1 , 7)  ,//DayOfWeek 星期   取值（1-7[1=星期日]）或者（SUN, MON, TUE, WED, THU, FRI, SAT）
    WEEK  (5 , 0 , 6),//DayOfWeek 星期 取值（0-6[0=星期日]）或者（SUN, MON, TUE, WED, THU, FRI, SAT）
    YEAR  (6 , 2019 , 2099);//年份
	
	private int position;//在cron表达式中的位置
	private Integer min;//该域最小值
	private Integer max;//该域最大值
	/**
	 * @param position
	 * @param min
	 * @param max
	 */
	private CronPosition(int position, Integer min, Integer max) {
		this.position = position;
		this.min = min;
		this.max = max;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	/**
	 * 根据位置计算枚举域的静态方法
	 * @param position
	 * @return
	 */
	public static CronPosition fromPosition(int position){
		CronPosition[] values = CronPosition.values();
		for (CronPosition field : values) {
			if(position == field.position){
				return field;
			}
		}
		return null;
	}
}

package com.smartflow.cron.pojo;

import java.util.Calendar;

/**
 * 保存年月日
 * @author smartflow
 *
 */
public final class DayOfYear implements Comparable<DayOfYear> {

	private int day;
	private int month;
	private int year;
	
	/**
	 * @param day
	 * @param month
	 * @param year
	 */
	public DayOfYear(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}



	public int getMonth() {
		return month;
	}



	public int getYear() {
		return year;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		DayOfYear other = (DayOfYear) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
//		if (year != other.year)
//			return false;
		return year == other.year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
//		int result = 1;
		int result = day;
		//result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}
	/**
	 * 计算星期
	 * @return
	 */
	public int week() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.DAY_OF_MONTH, getDay());
		calendar.set(calendar.MONTH, getMonth() - 1);
//		System.out.println(Calendar.DAY_OF_WEEK);//定值7
//		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));//获取星期几（0-6）
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;//周日-周六（0-6）
//		return calendar.get(calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(calendar.DAY_OF_WEEK);//周日-周六(1-7)
	}

	/**
	 * 按照年月日的顺序逐个比较
	 */
	@Override
	public int compareTo(DayOfYear o) {
		if(this.getYear() > o.getYear()) {
			return 1;
		}
		if(this.getYear() < o.getYear()) {
			return -1;
		}
		if(this.getMonth() > o.getMonth()) {
			return 1;
		}
		if(this.getMonth() < o.getMonth()) {
			return -1;
		}
		if(this.getDay() > o.getDay()) {
			return 1;
		}
		if(this.getDay() < o.getDay()) {
			return -1;
		}
		return 0;
	}
	

}

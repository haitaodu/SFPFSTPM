package com.smartflow.cron.pojo;

import com.smartflow.cron.util.DateUtil;
/**
 * 保存时分秒
 * @author smartflow
 *
 */
public final class TimeOfDay implements Comparable<TimeOfDay>{
	private int hour;
	private int minute;
	private int second;
	/**
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public TimeOfDay(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "TimeOfDay [hour=" + hour + ", minute=" + minute + ", second=" + second + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		TimeOfDay other = (TimeOfDay) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
//		if (second != other.second)
//			return false;
//		return true;
		return second == other.second;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
//		int result = 1;
//		result = prime * result + hour;
		int result = hour;
		result = prime * result + minute;
		result = prime * result + second;
		return result;
	}
	/**
	 * 按照时分秒的顺序逐个比较
	 */
	@Override
	public int compareTo(TimeOfDay o) {
		if(this.getHour() > o.getHour()) {
			return 1;
		}
		if(this.getHour() < o.getHour()) {
			return -1;
		}
		if(this.getMinute() > o.getMinute()) {
			return 1;
		}
		if(this.getMinute() < o.getMinute()) {
			return -1;
		}
		if(this.getSecond() > o.getSecond()) {
			return 1;
		}
		if(this.getSecond() < o.getSecond()) {
			return -1;
		}
		return 0;
	}
	
	public boolean equalsWithTolerance(TimeOfDay another, int seconds) {
		return DateUtil.equalsWithTolerance(this, another, seconds);
	}
}

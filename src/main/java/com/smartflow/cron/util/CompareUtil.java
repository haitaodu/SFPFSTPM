package com.smartflow.cron.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import com.smartflow.cron.pojo.CronPosition;

public class CompareUtil {

	/**
	 * 从小到大排好序的列表中找第一个符合的
	 * @param current
	 * @param sortedList
	 * @return
	 */
	public static <T extends Comparable<T>> T findNext(T current, List<T> sortedList){
		for (T item : sortedList) {
			if (item.compareTo(current) >= 0) {
				return item;
			}
		}
		throw new IllegalArgumentException("超出范围了");
	}
	
	/**
	 * 利用Set列表去重，要求<T>必须实现hashCode和equals方法
	 * @param list
	 */
	public static <T> void removeDuplicate(Collection<T> list){
		LinkedHashSet<T> set = new LinkedHashSet<>(list.size());
		set.addAll(list);
		list.clear();
		list.addAll(set);
	}
	/**
	 * 比较大小，左边的必须比右边的小
	 * @param left
	 * @param right
	 */
	public static void assertSize(int left, int right) {
		if(left > right) {
			throw new IllegalArgumentException("right should bigger than left,but find "+ left + " > " + right);
		}
	}
	/**
	 * 某个域的范围
	 * @param cronPosition
	 * @param value
	 */
	public static void assertRange(CronPosition cronPosition, int value){
		int min = cronPosition.getMin();
		int max = cronPosition.getMax();
		if(value < min || value > max) {
			throw new IllegalArgumentException(cronPosition.name()+" 域[" + min + "，" + max + "]，but find "+value);
		}
	}
	
	/**
	 * 判断给定值是否在集合中(满足cron表达式)
	 * @param num 给定值
	 * @param list 集合
	 * @return true在集合中/false不在集合中
	 */
	public static <T> boolean inList(T num, List<T> list) {
		for (T tmp : list) {
			if(tmp.equals(num)) {
				//相同要执行
				return true;
			}
		}
		return false;
	}
}

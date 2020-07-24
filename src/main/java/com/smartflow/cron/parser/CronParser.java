package com.smartflow.cron.parser;

import java.util.Date;
import java.util.List;

import com.smartflow.cron.pojo.TimeOfDay;
/**
 * CRON表达式解析
 * @author smartflow
 *
 */
public interface CronParser {
	/**
	 * 某个时刻的下一个时刻
	 * @param date
	 * @return
	 */
	Date next(Date date);
	/**
	 * 计算一天中的哪些时刻[时分秒]执行
	 * @param date
	 * @return
	 */
	List<TimeOfDay> timesOfDay(Date date);
}

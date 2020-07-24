package com.smartflow.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import com.smartflow.cron.parser.DayBasedCronParser;
import com.smartflow.cron.pojo.CronField;
import com.smartflow.cron.pojo.TimeOfDay;
import com.smartflow.cron.util.CronUtils;
import com.smartflow.cron.util.DateUtil;

public class CronTest {

	/**
	 * 计算年月周日时分秒和给定值是否相等
	 */
	@Test
	public void testCaculate() {
		//星期日到星期六（1-7）
		Date date = DateUtil.toDate("2019-08-04 14:59:22");
		Assert.assertEquals(2019, DateUtil.year(date));
		Assert.assertEquals(8, DateUtil.month(date));
		Assert.assertEquals(0, DateUtil.week(date));//星期天
		Assert.assertEquals(4, DateUtil.day(date));
		Assert.assertEquals(14, DateUtil.hour(date));
		Assert.assertEquals(59, DateUtil.minute(date));
		Assert.assertEquals(22, DateUtil.second(date));
		
		date = DateUtil.toDate("2019-07-28 14:59:22");
		Assert.assertEquals(0, DateUtil.week(date));//星期天
	}
	/**
	 * 将cron表达式切割成数组
	 */
	@Test
	public void testCut() {
		String cron = "0 15 10 ? * * ";
		System.out.println(CronUtils.cut(cron));
		Assert.assertEquals(Arrays.asList("0", "15", "10", "?", "*", "*"), CronUtils.cut(cron));
	}
	
	@Test
	public void testConvertField() {
		List<CronField> cronFields = CronUtils.convertCronField("0 0-5 14/2 * * ?");
		for (int i = 0; i < 6; i++) {
			CronField field = cronFields.get(i);
			Assert.assertEquals(field.getCronPosition().getPosition(), i);
		}
		
		Assert.assertEquals("0", cronFields.get(0).getExpress());
		Assert.assertEquals("0-5", cronFields.get(1).getExpress());
		Assert.assertEquals("14/2", cronFields.get(2).getExpress());
		Assert.assertEquals("*", cronFields.get(3).getExpress());
		Assert.assertEquals("*", cronFields.get(4).getExpress());
		Assert.assertEquals("*", cronFields.get(5).getExpress());
		List<CronField> fields = CronUtils.convertCronField("0 15 10 ? JAN-NOV MON-FRI");
		for (int i = 0; i < 6; i++) {
			CronField field = fields.get(i);
			Assert.assertEquals(field.getCronPosition().getPosition(), i);
		}
		Assert.assertEquals("0", fields.get(0).getExpress());
		Assert.assertEquals("15", fields.get(1).getExpress());
		Assert.assertEquals("10", fields.get(2).getExpress());
		Assert.assertEquals("*", fields.get(3).getExpress());
		Assert.assertEquals("1-11", fields.get(4).getExpress());
		Assert.assertEquals("1-5", fields.get(5).getExpress());
		
		//包含年域的情况
		cronFields = CronUtils.convertCronField("0 15 10 ? JAN-NOV MON-FRI 2019");
		System.out.println(cronFields);
	}
	
	@Test
	public void testCal() {
		Date date = DateUtil.toDate("2019-08-11 12:00:12");//星期天
		//List<TimeOfDay> calculate = CronUtils.calculate("0 15 10 ? * *", date);
		//Assert.assertEquals(Collections.singletonList(new TimeOfDay(10, 15, 0)), calculate);
		//10点15分0-5秒  "-"指定范围
		
		String cron = "0-5 15 10 ? * *";
		List<TimeOfDay> calculate = null;
	
		calculate = CronUtils.timesOfDay(cron, date);
		
		Assert.assertEquals(Arrays.asList(
				new TimeOfDay(10, 15, 0),
				new TimeOfDay(10, 15, 1),
				new TimeOfDay(10, 15, 2),
				new TimeOfDay(10, 15, 3),
				new TimeOfDay(10, 15, 4),
				new TimeOfDay(10, 15, 5)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
		
		//10点1分开始，每10分钟执行一次
		cron = "0 1/10 10 ? * *";
		calculate = CronUtils.timesOfDay(cron, date);
		Assert.assertEquals(Arrays.asList(
				new TimeOfDay(10, 1, 0),
				new TimeOfDay(10, 11, 0),
				new TimeOfDay(10, 21, 0),
				new TimeOfDay(10, 31, 0),
				new TimeOfDay(10, 41, 0),
				new TimeOfDay(10, 51, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
		
		// "," 指定10点几分执行
		cron = "0 1,4,6,8,10,50 10 ? * *";
		calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
                new TimeOfDay(10 , 1 , 0),
                new TimeOfDay(10 , 4 , 0),
                new TimeOfDay(10 , 6 , 0),
                new TimeOfDay(10 , 8 , 0),
                new TimeOfDay(10 , 10 , 0),
                new TimeOfDay(10 , 50 , 0)) , calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
      
        //10点1-30分钟内，每隔5分钟执行一次
        cron = "0 1-30/5 10 ? * *";
        calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
                new TimeOfDay(10 , 1 , 0),
                new TimeOfDay(10 , 6 , 0),
                new TimeOfDay(10 , 11 , 0),
                new TimeOfDay(10 , 16 , 0),
                new TimeOfDay(10 , 21 , 0),
                new TimeOfDay(10 , 26 , 0)) , calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
        
        //（当前星期的）周日的10点1-30分钟内，每隔5分钟执行一次
        cron = "0 1-30/5 10 ? * SUN";
        calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
        		new TimeOfDay(10 , 1, 0),
        		new TimeOfDay(10 , 6 , 0),
                new TimeOfDay(10 , 11 , 0),
                new TimeOfDay(10 , 16 , 0),
                new TimeOfDay(10 , 21 , 0),
                new TimeOfDay(10 , 26 , 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
                
       //指定8月的10点1-30分钟内，每隔5分钟执行一次
        cron = "0 1-30/5 10 ? 8 *";
        calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
        		new TimeOfDay(10, 1, 0),
        		new TimeOfDay(10, 6, 0),
        		new TimeOfDay(10, 11, 0),
        		new TimeOfDay(10, 16, 0),
        		new TimeOfDay(10, 21, 0),
        		new TimeOfDay(10, 26, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
         
		
		//指定8月的10点1-4分钟、43分钟执行一次
	    cron = "0 1-4,43 10 ? 8 *";
        calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
        		new TimeOfDay(10, 1, 0),
        		new TimeOfDay(10, 2, 0),
        		new TimeOfDay(10, 3, 0),
        		new TimeOfDay(10, 4, 0),
        		new TimeOfDay(10, 43, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
		
		//指定8月10时1-10分每隔2分钟执行一次，43分执行一次
		cron = "0 1-10/2,43 10 ? 8 *";
        calculate = CronUtils.timesOfDay(cron, date);
        Assert.assertEquals(Arrays.asList(
        		new TimeOfDay(10, 1, 0),
        		new TimeOfDay(10, 3, 0),
        		new TimeOfDay(10, 5, 0),
        		new TimeOfDay(10, 7, 0),
        		new TimeOfDay(10, 9, 0),
        		new TimeOfDay(10, 43, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
         
		//指定8月的10时7分执行、1-5分每隔2分钟执行一次、5分执行、6分执行
		cron = "0 7,1-5/2,5,6 10 ? 8 *";
		calculate = CronUtils.timesOfDay(cron, date);
		Assert.assertEquals(Arrays.asList(
				new TimeOfDay(10, 1, 0),
				new TimeOfDay(10, 3, 0),
				new TimeOfDay(10, 5, 0),
				new TimeOfDay(10, 6, 0),
				new TimeOfDay(10, 7, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
	
		
		//指定8月的10时1-6分每隔2分执行一次、12-27分每隔2分执行一次
		cron = "0 1-6/2,12-27/5 10 ? 8 *";
		calculate = CronUtils.timesOfDay(cron, date);
		Assert.assertEquals(Arrays.asList(
				new TimeOfDay(10, 1, 0),
				new TimeOfDay(10, 3, 0),
				new TimeOfDay(10, 5, 0),
				new TimeOfDay(10, 12, 0),
				new TimeOfDay(10, 17, 0),
				new TimeOfDay(10, 22, 0),
				new TimeOfDay(10, 27, 0)), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
		
		//星期一到星期六执行，星期天不执行就返回空集合
		cron = "0 1-30/5 10 ? * MON-SAT";
		calculate = CronUtils.timesOfDay(cron, date);
		Assert.assertEquals(Collections.emptyList(), calculate);
		Assert.assertEquals(calculate, new DayBasedCronParser(cron).timesOfDay(date));
	}
	/**
	 * right should bigger than left,but find 5 > 0
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException1(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("5-0 15 10 ? * *", date);
	}
	/**
	 * SECOND 域[0，59]，but find 62
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException2(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("1-62 15 10 ? * *", date);
	}
	/**
	 * MINUTE 域[0，59]，but find 78
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException3(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("1 2-78 10 ? * *", date);
	}
	/**
	 * HOUR 域[0，23]，but find 25
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException4(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("2 15 25 ? * *", date);
	}
	/**
	 * DAY 域[1，31]，but find 32
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException5(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("2 15 23 2-32 * *", date);
	}
	/**
	 * MONTH 域[1，12]，but find 13
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testException6(){
		Date date = DateUtil.toDate("2019-08-11 10:22:00");
		CronUtils.timesOfDay("2 15 23 3 1-13/2 *", date);
	}
}

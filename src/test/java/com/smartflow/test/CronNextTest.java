package com.smartflow.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.scheduling.support.CronSequenceGenerator;

import com.smartflow.cron.util.CronUtils;
import com.smartflow.cron.util.DateUtil;
import com.smartflow.util.GetDateByCRONExpressionUtil;


public class CronNextTest {

	@Test
	public void testNext0(){
		Date date = DateUtil.toDate("2019-08-11 13:12:00");
		String cron = "0 30 2 ? * WED";//当前日期的下一个周三的2:30执行
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-14 02:30:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-14 02:30:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext1(){
		Date date = DateUtil.toDate("2019-08-08 11:16:00");
		String cron = "2 15 12 ? * *";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-08 12:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-08 12:15:02", DateUtil.toStr(next1));
	}

	@Test
	public void testNext2(){
		Date date = DateUtil.toDate("2019-08-08 13:16:00");
		String cron = "0 0 8 * * *";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-09 08:00:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-09 08:00:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext3(){
		Date date = DateUtil.toDate("2019-08-08 13:00:11");
		String cron = "0/2 1 * * * *";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-08 13:01:12", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		//13:01:12??? //需要每个域大于当前的???
		Assert.assertEquals("2019-08-08 13:01:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext4(){
		Date date = DateUtil.toDate("2019-08-08 13:28:24");
		String cron = "0 0/5 14,18 * * ?";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-08 14:00:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-08 14:00:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext5(){
		Date date = DateUtil.toDate("2019-08-08 13:28:24");
		String cron = "0 15 10 ? * MON-FRI";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-09 10:15:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-09 10:15:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext6(){
		Date date = DateUtil.toDate("2019-08-09 13:28:24");
		String cron = "0 15 10 ? * MON-FRI";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-12 10:15:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-12 10:15:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext7(){
		Date date = DateUtil.toDate("2019-08-09 13:28:24");
		String cron = "10-20/4 10,44,30/2 10 ? 3 WED";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2020-03-04 10:10:10", DateUtil.toStr(next));//周三

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2020-03-04 10:10:10", DateUtil.toStr(next1));
	}

	@Test
	public void testNext8(){
		Date date = DateUtil.toDate("2019-09-09 13:28:24");
		String cron = "0 0 0 1/2 MAR-AUG ?";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2020-03-01 00:00:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2020-03-01 00:00:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext9(){
		Date date = DateUtil.toDate("2019-08-10 09:13:24");//周六
		String cron = "0 10-20/3,57-59 * * * WED-FRI";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-14 00:10:00", DateUtil.toStr(next));//周三

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-14 00:10:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext10(){
		Date date = DateUtil.toDate("2019-08-08 09:13:24");
		String cron = "0 10,44 14 ? 3 WED";//3月周三
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2020-03-04 14:10:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2020-03-04 14:10:00", DateUtil.toStr(next1));
	}

	@Test
	public void testNext11(){
		Date date = DateUtil.toDate("2019-08-08 13:16:24");
		String cron = "0-12/12 00 12 ? * *";
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-08-09 12:00:00", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-08-09 12:00:00", DateUtil.toStr(next1));
	}
	/**
	 * 测试"L"出现在周上
	 */
	@Test
	public void testL1(){
		try {
			String cron = "0 30 2 ? * L";//每周的最后一天(星期六) 2:30执行
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Date> dateList = GetDateByCRONExpressionUtil.getDateFromCRONExpression(new Date(),sdf.parse("2020-01-01 00:00:00"), cron);
			for (Date date : dateList) {
				System.out.println(date.toLocaleString());
			}
			
		//报错，不支持L字符的cron表达式
//		Date next = new CronSequenceGenerator(cron).next(date);
//		Assert.assertEquals("2019-08-16 02:30:00", DateUtil.toStr(next));
			Date next1 = CronUtils.next(cron, new Date());
			Assert.assertEquals("2019-08-17 02:30:00", DateUtil.toStr(next1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Test
	public void testL2(){
		try {
			String cron = "0 30 2 ? * 1L";//每月的最后一个星期天2:30执行
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Date> dateList = GetDateByCRONExpressionUtil.getDateFromCRONExpression(new Date(),sdf.parse("2020-01-01 00:00:00"), cron);
			for (Date date : dateList) {
				System.out.println(date.toLocaleString());
			}
			
		//报错，不支持L字符的cron表达式
//		Date next = new CronSequenceGenerator(cron).next(date);
//		Assert.assertEquals("2019-08-16 02:30:00", DateUtil.toStr(next));
			Date next1 = CronUtils.next(cron, new Date());
			Assert.assertEquals("2019-08-17 02:30:00", DateUtil.toStr(next1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	/************************** 极端情况**************************/
	@Test
	public void testNext12(){
		Date date = DateUtil.toDate("2018-08-08 13:16:24");
		String cron = "2 15 23 3 3 7";//要求3月3号，星期天
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2019-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2019-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNext13(){
		Date date = DateUtil.toDate("2019-08-08 13:16:24");
		String cron = "2 15 23 3 3 6";//19年3月3号是星期天 29年3月3号星期六
		//报错
//		Date next = new CronSequenceGenerator(cron).next(date);
//		Assert.assertEquals("2029-03-03 23:15:02", DateUtil.toStr(next));

		//报错 限制不能超过10年
		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2029-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test
	public void testNext14(){
		Date date = DateUtil.toDate("2019-08-08 13:16:24");
		String cron = "2 15 23 3 3 5";//23年3月3号星期五
		//报错
//		Date next = new CronSequenceGenerator(cron).next(date);
//		Assert.assertEquals("2023-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2023-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test
	public void testNext15(){
		Date date = DateUtil.toDate("2019-08-08 13:16:24");
		String cron = "2 15 23 3 3 4";//22年3月3号星期四

		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2022-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2022-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test
	public void testNext16(){
		Date date = DateUtil.toDate("2019-08-08 13:16:24");
		String cron = "2 15 23 3 3 3";//21年3月3号星期三

		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2021-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2021-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test
	public void testNext17(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "2 15 23 3 3 2";//20年3月3号星期二

//		Date date = DateUtil.toDate("2018-08-08 13:16:24"); //next会报错Overflow in day for expression "2 15 23 3 3 2"
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2020-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2020-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNext18(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "2 15 23 3 3 1";//25年3月3号星期一

		//异常  报错
		Date next = new CronSequenceGenerator(cron).next(date);
		Assert.assertEquals("2025-03-03 23:15:02", DateUtil.toStr(next));

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2025-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	/**
	 * 有年域，满足
	 */
	@Test
	public void testNext19(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "2 15 23 3 3 1 2019,2025";//25年3月3号星期一

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2025-03-03 23:15:02", DateUtil.toStr(next1));
	}
	
	/**
	 * 有年域的，不满足
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNext20(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "2 15 23 3 3 1 2019,2020,2024";

		Date next1 = CronUtils.next(cron, date);
		Assert.assertEquals("2025-03-03 23:15:02", DateUtil.toStr(next1));	
	}
	/**
	 * 一些比较复杂的表达式来测试benchmark
	 * 1.由于Spring使用了BitSet数据结构，操作都是位运算，所以速度比较快
	 * 2.HMS算法先计算所有的可能，再去找一个，比较耗时，并且基于一个假设，时分秒的组合不多，在遇到极端情况表现就非常糟糕
	 */
	@Ignore
	@Test
	public void benchmark(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "10-20/4 10,44,30/2 10 ? 3 WED";
		int max = 10000;
		long beginSpring = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			new CronSequenceGenerator(cron).next(date);
		}
		System.out.println("Spring 执行 " + max + "次耗时: " + (System.currentTimeMillis() - beginSpring));
		
		long beginHms = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			CronUtils.next(cron, date);
		}
		System.out.println("HMS 执行 " + max + " 次耗时: " + (System.currentTimeMillis() - beginHms));	
	}
	@Ignore
	@Test
	public void benchmark2(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "0 0/5 14,18 * * ?";
		int max = 10000;
		long beginSpring = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			new CronSequenceGenerator(cron).next(date);			
		}
		System.out.println("Spring 执行 " + max + "次耗时: " + (System.currentTimeMillis() - beginSpring));
		
		long beginHms = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			CronUtils.next(cron, date);
		}
		System.out.println("HMS 执行 " + max + " 次耗时: " + (System.currentTimeMillis() - beginHms));		
	}
	
	@Ignore
	@Test
	public void benchmark3(){
		Date date = DateUtil.toDate("2019-09-08 13:16:24");
		String cron = "0 10-20/3,57-59 * * * WED-FRI";
		int max = 10000;
		long beginSpring = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			new CronSequenceGenerator(cron).next(date);			
		}
		System.out.println("Spring 执行 " + max + "次耗时: " + (System.currentTimeMillis() - beginSpring));
		
		long beginHms = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			CronUtils.next(cron, date);
		}
		System.out.println("HMS 执行 " + max + " 次耗时: " + (System.currentTimeMillis() - beginHms));		
	
	}
}

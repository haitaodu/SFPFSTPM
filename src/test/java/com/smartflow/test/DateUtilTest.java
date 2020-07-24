package com.smartflow.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.smartflow.cron.pojo.TimeOfDay;
import com.smartflow.cron.util.DateUtil;

public class DateUtilTest {

	@Test
	public void testCalculate(){
		Date date = DateUtil.toDate("2019-08-07 10:53:46");
		Assert.assertEquals(2019, DateUtil.year(date));
		Assert.assertEquals(8, DateUtil.month(date));
		Assert.assertEquals(3, DateUtil.week(date));//星期三
		Assert.assertEquals(7, DateUtil.day(date));
		Assert.assertEquals(10, DateUtil.hour(date));
		Assert.assertEquals(53, DateUtil.minute(date));
		Assert.assertEquals(46, DateUtil.second(date));
		
		date = DateUtil.toDate("2019-08-11 10:53:46");
		Assert.assertEquals(0, DateUtil.week(date));//星期天
	}
	
	@Test
	public void testEqualsWithTolerance(){
		TimeOfDay base = new TimeOfDay(1, 2, 3);
		Assert.assertTrue(base.equalsWithTolerance(new TimeOfDay(1, 2, 3), 0));
		
		Assert.assertTrue(base.equalsWithTolerance(new TimeOfDay(1, 2, 4), 1));
		Assert.assertFalse(base.equalsWithTolerance(new TimeOfDay(1, 2, 5), 1));
		
		Assert.assertTrue(base.equalsWithTolerance(new TimeOfDay(1, 3, 4), 61));
		Assert.assertTrue(base.equalsWithTolerance(new TimeOfDay(2, 2, 5), 60*60+2));
		
		Assert.assertFalse(base.equalsWithTolerance(new TimeOfDay(1, 3, 4), 1));//61
		Assert.assertFalse(base.equalsWithTolerance(new TimeOfDay(1, 3, 4), 60));
		Assert.assertFalse(base.equalsWithTolerance(new TimeOfDay(2, 2, 4), 60*60));
	}
	
}

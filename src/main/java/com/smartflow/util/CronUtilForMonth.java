package com.smartflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.id.enhanced.TableStructure;

import com.smartflow.cron.pojo.CronField;
import com.smartflow.cron.util.CronUtils;

public class CronUtilForMonth{
	public static Boolean isCron(String cron) {
		// TODO Auto-generated method stub
	List<CronField> crons=CronUtils.convertCronField(cron);
	int number=0;
	for (CronField cronField : crons) {
	
		if (!cronField.getExpress().equals("0")) {
			if (number<=2) {
				return false;
			}
		}
		number++;
	}
		return true;
	}


	public static  List<Date> getDatesForCron(String cron, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		
			
	
		List<Date> dates=new ArrayList<>();
		try {
	  List<Integer> years=CRONUtil.getYearFromCron(cron, beginDate, endDate);
	  List<Integer> months=CRONUtil.getMonthFromCron(cron);
	  List<Integer> days=CRONUtil.getDayFromCron(cron);
	  SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  for (Integer year : years) {
		for (Integer month : months) {
			for (Integer day : days) {
				
				String timeForString=year+"-"+month+"-"+day+" "+"00:00:00";
				
					Date date=utcFormat.parse(timeForString);
			        dates.add(date);
				
			}
		}
	}
	
		return dates;
	
	} catch (ParseException e) {
		// TODO: handle exception
		e.printStackTrace();
	 
	}
		finally {
			return dates;
		}

}
	
}

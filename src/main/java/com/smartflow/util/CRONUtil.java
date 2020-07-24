package com.smartflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.smartflow.cron.pojo.CronField;
import com.smartflow.cron.util.CronUtils;

public class CRONUtil {
	private static final String TRANS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TRANS_CRON_FORMAT_ONCE = "ss mm HH dd MM ? yyyy";

    private static final String TRANS_CRON_FORMAT_DAY = "ss mm HH dd/ * ? *";

    private static final String TRANS_CRON_FORMAT_WEEK = "ss mm HH ? * -- *";

    private static final String TRANS_CRON_FORMAT_MONTH = "ss mm HH dd MM/1 ? *";

    public static void main(String[] args) throws Exception {
       // String result = getCron("day", "2018-08-11 12:11:00");
      // String result = getCron("MON", "2018-08-11 12:11:00");
       //String result = getCronByOnce("2017-01-01 12:12:12");
//       String result = getCron("month", "2019-01-01 12:00:00");
        String result = getCronToDate("0 0 12 * * ?");

        System.out.println(result);
    }

    /**
     * 生成只执行一次的cron
     */
    public static String getCronByOnce(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date parse = format.parse(dateStr);
        return fmtDateToStr(parse, TRANS_CRON_FORMAT_ONCE);
    }

    /**
     * 生成每月或每周或每天执行的cron
     */
    public static String getCron(String period, String startDateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date startDate = format.parse(startDateStr);
        StringBuffer cronStringBuffer = new StringBuffer();
        if ("month".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_MONTH).trim();
            cronStringBuffer.append(startDateCron);
        } else if ("day".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_DAY).trim();
            // StringBuffer stringBuffer = new StringBuffer(str);
            // stringBuffer.insert(stringBuffer.lastIndexOf("/") + 1, period);
            // cron = stringBuffer.toString().trim();
            // createPeriod(arrStart, arrEnd, 4);
            cronStringBuffer.append(startDateCron).insert(cronStringBuffer.lastIndexOf("/") + 1, "1");
        } else {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_WEEK).trim();
            cronStringBuffer.append(startDateCron.replaceAll("--", period));
        }
        return cronStringBuffer.toString();
    }


    public static String getCronToDate(String cron) {
        String format = null;
        StringBuffer stringBuffer = new StringBuffer(cron);
        int lastIndexOf = stringBuffer.lastIndexOf("/");
        //stringBuffer.deleteCharAt(lastIndexOf);
        //stringBuffer.deleteCharAt(lastIndexOf);
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(stringBuffer.toString());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = sdf.format(date);
        } catch (Exception e) {
            return null;
        }
        return format;
    }

    /**
     * 格式转换 日期-字符串 
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    
    public static List<Integer> getYearFromCron(String cron,Date beginDate,Date endDate)
    {
    	List<CronField> crons=CronUtils.convertCronField(cron);
    	List<Integer> years=new ArrayList<>();
    	String yearForCron=crons.get(crons.size()-1).getExpress();
    	Calendar c = Calendar.getInstance();
    	c.setTime(beginDate);    	
    	int beginYear=c.get(Calendar.YEAR);
    	c.setTime(endDate);
    	int endYear=c.get(Calendar.YEAR);
    	if (yearForCron.equals("*")) {
		return addYear(beginYear, endYear, years);
		}
    	else if (yearForCron.matches("\\d\\d\\d\\d-\\d\\d\\d\\d")) {
			String[] yearsForSplit=yearForCron.split("-");
			beginYear=Integer.parseInt(yearsForSplit[0]);
			endYear=Integer.parseInt(yearsForSplit[1]);
			return addYear(beginYear, endYear, years);
		}
    	else if (yearForCron.matches("\\d\\d\\d\\d")) {
			years.add(Integer.parseInt(yearForCron));
			return years;
		}
    	return years;
    }
    
    public static List<Integer> getMonthFromCron(String cron)
    {
    	List<CronField> crons=CronUtils.convertCronField(cron);
    	String monthForCron=crons.get(crons.size()-3).getExpress();
    	List<Integer> months=new ArrayList<>();
    	
    	//addYear应该写成通用组件的
    	if (monthForCron.equals("*")) {
    		return addYear(1, 12, months);
    		}
    	else if (monthForCron.matches("[0-9]{1,2}-[0-9]{1,2}")) {
			String[] monthsForSplit=monthForCron.split("-");
			int beginMonth=Integer.parseInt(monthsForSplit[0]);
			int endMonth=Integer.parseInt(monthsForSplit[1]);
			return addYear(beginMonth, endMonth, months);
		}
    	else if (monthForCron.matches("[0-9]{1,2}")) {
    		months.add(Integer.parseInt(monthForCron));
			return months;
    	}
    	return months;
    }
    
    public static List<Integer> getDayFromCron(String cron)
    {
    	List<CronField> crons=CronUtils.convertCronField(cron);
    	String dayForCron=crons.get(crons.size()-4).getExpress();
    	List<Integer> days=new ArrayList<>();
    	
    	//addYear应该写成通用组件的
    	if (dayForCron.equals("*")) {
    		return addYear(1, 31, days);
    		}
    	else if (dayForCron.matches("[0-9]{1,2}-[0-9]{1,2}")) {
			String[] daysForSplit=dayForCron.split("-");
			int beginMonth=Integer.parseInt(daysForSplit[0]);
			int endMonth=Integer.parseInt(daysForSplit[1]);
			return addYear(beginMonth, endMonth, days);
		}
    	else if (dayForCron.matches("[0-9]{1,2}")) {
    		days.add(Integer.parseInt(dayForCron));
			return days;
    	}
    	return days;
    }
    
    public static List<Integer> getWeekFromCron(String cron,Integer year)
    {
    	try {
			
	
    	final int weekOfNumber=52;
    	Calendar calendar=Calendar.getInstance();     
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
        calendar.set(Calendar.YEAR, year);
        List<CronField> crons=CronUtils.convertCronField(cron);
    	String weekForCron=crons.get(crons.size()-2).getExpress();
    	List<Integer> weeks=new ArrayList<>();
    	if (weekForCron.equals("*")) {
    		return addYear(1, 31, weeks);
		}
    	else if (weekForCron.matches("\\d/\\d")) {
    		int week=1;
    		while(week<=weekOfNumber)
    		{
    			String[] weeksForSplit=weekForCron.split("/");
    			calendar.set(Calendar.YEAR, year); 
    			calendar.set(Calendar.WEEK_OF_YEAR, week);
    			int beginWeekDay=Integer.parseInt(weeksForSplit[0]);
    			int endWeekDay=Integer.parseInt(weeksForSplit[1]);
    			while(beginWeekDay<=endWeekDay)
    			{
    				calendar.set(Calendar.DAY_OF_WEEK, beginWeekDay);
    				weeks.add(calendar.get(Calendar.DAY_OF_MONTH));
    				beginWeekDay++;
    			}
    			week++;
    		}
		
    		return weeks;
		}else if (weekForCron.matches("\\d#\\d")) {
			String[] weeksForSplit=weekForCron.split("#");
			int week=Integer.parseInt(weeksForSplit[0]);
			int dayOfWeek=Integer.parseInt(weeksForSplit[1]);
			calendar.set(Calendar.YEAR, year); 
			calendar.set(Calendar.WEEK_OF_YEAR, week);
			calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
			weeks.add(calendar.get(Calendar.DAY_OF_MONTH));
			
		}else if (weekForCron.matches("\\d")) {
			int week=1;
			while(week<=weekOfNumber)
    		{
				int dayOfWeek=Integer.parseInt(weekForCron);
    			calendar.set(Calendar.YEAR, year); 
    			calendar.set(Calendar.WEEK_OF_YEAR, week);
    			calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    			weeks.add(calendar.get(Calendar.DAY_OF_MONTH));
    		
    			week++;
    		}
		}
		return weeks;
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return new ArrayList<>();
		}
    }
    public static List<Integer> addYear(int beginYear,int endYear,List<Integer> years)
    {
    	while(beginYear<=endYear) {
			years.add(beginYear);
			beginYear++;
		}
    	return years;
    }
    
}

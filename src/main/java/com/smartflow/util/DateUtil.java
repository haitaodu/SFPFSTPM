package com.smartflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

public class DateUtil {
	 /**
     * 日期转化为cron表达式
     * @param date
     * @return
     */
    public static String getCron(java.util.Date  date){  
        String dateFormat="ss mm HH dd MM ? yyyy";  
        return  DateUtil.fmtDateToStr(date, dateFormat);  
    }  

    /**
     * cron表达式转为日期
     * @param cron
     * @return
     */
    public static Date getCronToDate(String cron) {  
        String dateFormat="ss mm HH dd MM ? yyyy";  
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        Date date = null;  
        try {  
            date = sdf.parse(cron);  
        } catch (ParseException e) {  
            return null;
        }  
        return date;  
    }  

    /** 
     * Description:格式化日期,String字符串转化为Date 
     *  
     * @param date 
     * @param dtFormat 
     *            例如:yyyy-MM-dd HH:mm:ss yyyyMMdd 
     * @return 
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
    
    public static void main(String[] args) {
    	Date date = new CronTrigger("0 25 11 5 * ?").nextExecutionTime(new SimpleTriggerContext());
		System.out.println(date);
	}
	
}

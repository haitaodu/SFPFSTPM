package com.smartflow.util;

import java.util.Date;
import java.util.List;

public interface CRONUtilForTPM {
	
	 Boolean isCron(String cron);
	
	List<Date> getDatesForCron(String cron,Date beginDate,Date endDate);

}

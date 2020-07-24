package com.smartflow.dao;

import java.util.List;

import com.smartflow.model.Reminder;

public interface ReminderDao {

	List<Reminder> getReminderList();
	
	List<Reminder> getReminderListByWorkPlanId(Integer Id);
}

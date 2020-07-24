package com.smartflow.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.model.Reminder;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.TPMWorkPlan_ReminderModel;
@Repository
public class ReminderDaoImpl implements ReminderDao{
@Autowired
HibernateTemplate  hibernate;
	@Override
	public List<Reminder> getReminderList() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Reminder> remindersList=(List<Reminder>)hibernate.find("FROM Reminder");
		return remindersList;
	}
	@Override
	public List<Reminder> getReminderListByWorkPlanId(Integer Id) {
		// TODO Auto-generated method stub
		List<Reminder> reminders=new ArrayList<>();
		@SuppressWarnings("unchecked")
		
		List<TPMWorkPlan_ReminderModel> tpmWorkPlan_Reminders= (List<TPMWorkPlan_ReminderModel>) 
		hibernate.findByNamedParam("FROM TPMWorkPlan_ReminderModel where TPMWorkPlanId=:TPMWorkPlanId", "TPMWorkPlanId", Id);
		//List<TPMWorkPlan_Reminder> tpmWorkPlan_Reminders=(List<TPMWorkPlan_Reminder>) 
				//hibernate.findByNamedParam("FROM TPMWorkPlan_Reminder where workPlan.id=:TPMWorkPlanId", "TPMWorkPlanId", Id);
	for (TPMWorkPlan_ReminderModel tpmWorkPlan_Reminder : tpmWorkPlan_Reminders) {
		Reminder reminder=hibernate.get(Reminder.class, tpmWorkPlan_Reminder.getReminderId());
		reminders.add(reminder);
	}
	
		return reminders;
	}

}

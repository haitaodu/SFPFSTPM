package com.smartflow.quartz;

import com.smartflow.model.Role;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkPlan;
import com.smartflow.service.AddAssignmentsListService;
import com.smartflow.service.MaintenanceTaskPlanService;
import com.smartflow.service.RemindsAndAssignmentsCalendarService;
import com.smartflow.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling//动态配置
@ContextConfiguration({"classpath*:/spring-*.xml"})
public class DynamicScheduledTask implements SchedulingConfigurer {

    @Autowired
    MaintenanceTaskPlanService maintenanceTaskPlanService;

    @Autowired
    AddAssignmentsListService addAssignmentsListService;

    @Autowired
    RemindsAndAssignmentsCalendarService remindsAndAssignmentsCalendarService;
    //时间表达式  每2秒执行一次
//  private String cron = "0/2 * * * * ?";
    private String cron = "0 03 18 ? * 3";
//    private String cron = "";
    private Integer workPlanId;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //任务逻辑
                if(!StringUtils.isEmpty(cron)){
                    System.out.println("---------------start-------------------");
                    System.out.println("动态修改定时任务参数，时间表达式cron为：" + cron);
                    System.out.println("当前时间为：" + sdf.format(new Date()));
                    System.out.println("----------------end--------------------");

                    WorkPlan workPlan = maintenanceTaskPlanService.getWorkPlanById(workPlanId);
                    WorkOrder workOrder = new WorkOrder();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date();
                    workOrder.setName(sdf.format(date));
                    workOrder.setWorkType(workPlan.getType());
                    workOrder.setFacilityId(workPlan.getFacilityId());
                    Integer creatorId = workPlan.getCreatorId();
                    workOrder.setState(1);
                    workOrder.setRole(workPlan.getRole());
                    workOrder.setCreatorId(creatorId);
                    workOrder.setCreationDateTime(date);
                    workOrder.setEditorId(creatorId);
                    workOrder.setEditDateTime(date);
                    workOrder.setStatus(1);
                    workOrder.setItemName(workPlan.getMaintenanceItemId() == 1 ? "点检" : "维护保养");
                    workOrder.setWorkPlanId(workPlan.getId());
                    remindsAndAssignmentsCalendarService.addWorkOrder(workOrder);
                    System.out.println("已定时向WorkOrder表添加数据");
                }
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if(!StringUtils.isEmpty(cron)) {
                    CronTrigger cronTrigger = new CronTrigger(cron);
                    Date nextExecDate = cronTrigger.nextExecutionTime(triggerContext);
                    return nextExecDate;
                }else{
                    return null;
                }
            }
        });
    }
    public void setCron(String cron) {
        this.cron = cron;
    }

    public void setWorkPlanId(Integer workPlanId) {
        this.workPlanId = workPlanId;
    }
}

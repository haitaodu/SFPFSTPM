package com.smartflow.util;

import com.smartflow.quartz.DynamicScheduledTask;
import com.smartflow.quartz.SpringDynamicCronTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath*:/spring-quartz.xml"})
@ContextConfiguration({"classpath*:/spring-*.xml"})
public class QuartzTest {

    @Autowired
    private DynamicScheduledTask dynamicScheduledTask;

    @Test
    public void test1(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        dynamicScheduledTask.setCron("0/10 * * * * ?");
        dynamicScheduledTask.setCron("0 54 17 ? * 3 *");//模拟每周三17:02
        System.out.println("cron表达式替换成功");
        dynamicScheduledTask.setWorkPlanId(1012);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
//        SpringDynamicCronTask task = new SpringDynamicCronTask().configureTasks();
    }
}

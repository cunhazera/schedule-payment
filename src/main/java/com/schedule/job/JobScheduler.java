package com.schedule.job;

import com.schedule.entity.Payment;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.time.ZoneOffset;
import java.util.Date;

public class JobScheduler {

    private Payment payment;

    public JobScheduler(Payment payment) {
        this.payment = payment;
    }

    public void schedule() throws SchedulerException {
        Date startDate = Date.from(payment.dueDate.toInstant(ZoneOffset.ofHours(-3)));
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        JobDataMap jobData = new JobDataMap();
        jobData.put("id", payment.id);
        jobData.put("email", payment.customerMail);
        JobDetail job = JobBuilder.newJob(PaymentJob.class)
                .withIdentity(String.format("job%d", payment.id), "payment-job-group")
                .setJobData(jobData)
                .build();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(String.format("trigger%d", payment.id), "trigger-group")
                .forJob(job)
                .startAt(startDate)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

}

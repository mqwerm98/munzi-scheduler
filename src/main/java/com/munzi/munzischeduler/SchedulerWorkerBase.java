package com.munzi.munzischeduler;

import com.munzi.munzischeduler.util.RandomUtil;
import com.munzi.munzischeduler.util.ValidationUtil;
import com.withwiz.plankton.scheduler.ASpringDynamicScheduler;
import lombok.Getter;
import lombok.Setter;
import org.quartz.CronExpression;
import org.springframework.scheduling.Trigger;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.*;

/**
 * scheduler worker base class
 */
@Getter
@Setter
public class SchedulerWorkerBase extends ASpringDynamicScheduler {

    /**
     * dynamic delay yn
     */
    protected boolean dynamicDelayYn;

    /**
     * delay
     */
    protected Integer delay;

    /**
     * cron
     */
    protected String cron;

    /**
     * zoneId (cron 기준시간)
     */
    protected ZoneId zoneId;

    /**
     * force off (강제 종료 시 true로 설정해서 사용)
     */
    protected boolean forceOff = false;



    /**
     * set scheduler trigger(delay)
     *
     * @return trigger
     */
    @Override
    public Trigger getTrigger() {
        return triggerContext -> {
            Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
            if (lastActualExecutionTime == null) lastActualExecutionTime = new Date();

            if (ValidationUtil.isExists(cron)) {
                try {
                    CronExpression cronExpression = new CronExpression(cron);
                    cronExpression.setTimeZone(TimeZone.getTimeZone(zoneId));
                    return cronExpression.getNextValidTimeAfter(lastActualExecutionTime);
                } catch (IllegalArgumentException | ParseException e) {
                    throw new IllegalArgumentException("Invalid cron", e);
                }
            } else {
                delay = delay == null ? 1000 : delay;

                int delayMilliSecond = dynamicDelayYn ? RandomUtil.randomInt(delay) : delay;
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastActualExecutionTime);
                calendar.add(Calendar.MILLISECOND, delayMilliSecond);
                return calendar.getTime();
            }
        };
    }

    /**
     * @param data for scheduled job
     */
    @Override
    public void processJob(Map data) {
        // default process is not work
    }
}

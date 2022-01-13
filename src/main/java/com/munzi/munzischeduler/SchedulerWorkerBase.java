package com.munzi.munzischeduler;

import com.munzi.munzischeduler.util.RandomUtil;
import com.munzi.munzischeduler.util.ValidationUtil;
import com.withwiz.plankton.scheduler.ASpringDynamicScheduler;
import lombok.Getter;
import lombok.Setter;
import org.quartz.CronExpression;
import org.springframework.scheduling.Trigger;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

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
                    return new CronExpression(cron).getNextValidTimeAfter(lastActualExecutionTime);
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

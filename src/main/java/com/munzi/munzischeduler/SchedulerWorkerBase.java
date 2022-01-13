package com.munzi.munzischeduler;

import com.munzi.munzischeduler.util.RandomUtil;
import com.withwiz.plankton.scheduler.ASpringDynamicScheduler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.Trigger;

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
    protected boolean dynamicDelay;

    /**
     * delay
     */
    protected int delay = 1000;

    /**
     * set scheduler trigger(delay)
     *
     * @return trigger
     */
    @Override
    public Trigger getTrigger() {

        return triggerContext -> {
            int seconds;
            if(dynamicDelay) {
                seconds = RandomUtil.randomInt(delay);
            } else {
                seconds = delay;
            }
//                log.debug("delay randomized: {}", seconds);

            Calendar nextExecutionTime = new GregorianCalendar();
            Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
            //you can get the value from wherever you want
            nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
            nextExecutionTime.add(Calendar.MILLISECOND, seconds);
            return nextExecutionTime.getTime();
        };
    }

    /**
     *
     * @param data for scheduled job
     */
    @Override
    public void processJob(Map data) {
        // default process is not work
    }
}

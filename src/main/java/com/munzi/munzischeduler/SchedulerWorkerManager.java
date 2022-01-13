package com.munzi.munzischeduler;

import com.munzi.munzischeduler.config.ASchedulerInstance;
import com.munzi.munzischeduler.config.SchedulerProperty;
import com.munzi.munzischeduler.util.ValidationUtil;
import org.springframework.context.ApplicationContext;

/**
 * scheduler worker manager class
 */
public class SchedulerWorkerManager {
    private final ASchedulerInstance schedulerInstance;

    public SchedulerWorkerManager(ASchedulerInstance schedulerInstance) {
        this.schedulerInstance = schedulerInstance;
    }

    /**
     * start scheduler work
     *
     * @param context ApplicationContext
     */
    public void startWorker(ApplicationContext context) {
        for (SchedulerProperty schedulerProperty : schedulerInstance.getSchedulerProperties()) {
            SchedulerWorkerBase schedulerWorkerBase = (SchedulerWorkerBase) context.getBean(schedulerProperty.getName());

            if (!ValidationUtil.isExists(schedulerProperty.getDelay()) && !ValidationUtil.isExists(schedulerProperty.getCron())) {
                throw new IllegalArgumentException("Either 'delay' or 'cron' must be entered.");
            }
            if (ValidationUtil.isExists(schedulerProperty.getDelay()) && ValidationUtil.isExists(schedulerProperty.getCron())) {
                throw new IllegalArgumentException("Only one of 'delay' and 'cron' should be used.");
            }

            schedulerWorkerBase.setDynamicDelayYn(schedulerProperty.isDynamicDelay());
            if (ValidationUtil.isExists(schedulerProperty.getDelay())) {
                schedulerWorkerBase.setDelay(schedulerProperty.getDelay());
            } else if (ValidationUtil.isExists(schedulerProperty.getCron())) {
                schedulerWorkerBase.setCron(schedulerProperty.getCron());
            }
            schedulerWorkerBase.begin();
        }
    }
}

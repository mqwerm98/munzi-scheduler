package com.munzi.munzischeduler;

import com.munzi.munzischeduler.config.ASchedulerInstance;
import com.munzi.munzischeduler.config.SchedulerProperty;
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

            if ((schedulerProperty.getDelay() == null || schedulerProperty.getDelay() <= 0) && (schedulerProperty.getCron() == null || schedulerProperty.getCron().isEmpty())) {
                throw new IllegalArgumentException("Either 'delay' or 'cron' must be entered.");
            }
            if ((schedulerProperty.getDelay() != null && schedulerProperty.getDelay() > 0) && (schedulerProperty.getCron() != null && !schedulerProperty.getCron().isEmpty())) {
                throw new IllegalArgumentException("Only one of 'delay' and 'cron' should be used.");
            }

            schedulerWorkerBase.setDynamicDelayYn(schedulerProperty.isDynamicDelay());
            if (schedulerProperty.getDelay() != null) {
                schedulerWorkerBase.setDelay(schedulerProperty.getDelay());
            } else if (schedulerProperty.getCron() != null && !schedulerProperty.getCron().isEmpty()) {
                schedulerWorkerBase.setCron(schedulerProperty.getCron());
            }
            schedulerWorkerBase.begin();
        }
    }
}

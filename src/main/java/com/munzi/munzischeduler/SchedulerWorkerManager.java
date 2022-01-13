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
            schedulerWorkerBase.setDynamicDelay(schedulerProperty.isDynamicDelay());
            schedulerWorkerBase.setDelay(schedulerProperty.getDelay());
            schedulerWorkerBase.begin();
        }
    }
}

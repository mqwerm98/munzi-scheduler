package com.munzi.munzischeduler.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Scheduler property
 */
@Setter
@Getter
@ToString
public class SchedulerProperty {

    /**
     * name
     */
    private String name;

    /**
     * implements class
     */
    private String className;

    /**
     * dynamic delay yn
     */
    private boolean dynamicDelay;

    /**
     * delay
     */
    private Integer delay;

    /**
     * cron
     */
    private String cron;

}

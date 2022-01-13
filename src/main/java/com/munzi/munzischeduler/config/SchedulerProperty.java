package com.munzi.munzischeduler.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SchedulerProperty {

    /**
     * name
     */
    String name;

    /**
     * implements class
     */
    String className;

    /**
     * dynamic delay yn
     */
    boolean dynamicDelay;

    /**
     * delay
     */
    int delay;

}

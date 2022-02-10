package com.munzi.munzischeduler.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  SchedulerProperties를 받을 instance.
 *  extends 받아 사용하도록 만들어졌다.
 */
@Setter
@Getter
@ToString
public abstract class ASchedulerInstance {

    private List<SchedulerProperty> schedulerProperties;

}

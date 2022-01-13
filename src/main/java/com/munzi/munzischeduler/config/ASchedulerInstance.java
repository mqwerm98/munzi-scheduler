package com.munzi.munzischeduler.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public abstract class ASchedulerInstance {

    List<SchedulerProperty> schedulerProperties;

}

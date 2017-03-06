package com.kii.cloud.rest.client.resource.thingif.states;

import com.kii.cloud.rest.client.model.thingif.TargetState;

public class AirConditionerState implements TargetState{
    public Boolean power;
    public Integer currentTemperature;

    public AirConditionerState(Boolean power, Integer currentTemperature) {
        this.power = power;
        this.currentTemperature = currentTemperature;
    }
}

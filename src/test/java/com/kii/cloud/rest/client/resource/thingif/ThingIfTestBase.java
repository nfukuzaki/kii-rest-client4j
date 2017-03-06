package com.kii.cloud.rest.client.resource.thingif;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.thingif.TargetState;
import com.kii.cloud.rest.client.resource.thingif.states.AirConditionerState;

public class ThingIfTestBase {
    protected static final String DEFAULT_THINGTYPE = "MyAirConditioner";
    protected static final String DEFAUTL_FIRMWARE_VERSION = "v1";
    protected static final String ALIAS1 = "AirConditionerAlias";

    protected JsonObject stateToJson(TargetState state) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        if (state instanceof AirConditionerState) {
            json.add(ALIAS1, gson.toJsonTree(state));
        } else {
            throw new RuntimeException("not supported state for test");
        }
        return json;
    }
}

package com.kii.cloud.rest.client.resource.thingif;

import com.kii.cloud.rest.client.*;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.thingif.TargetState;
import com.kii.cloud.rest.client.resource.KiiThingIfResource;
import com.kii.cloud.rest.client.resource.thingif.states.AirConditionerState;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingIfTargetStatesResourceTest extends ThingIfTestBase {

    @Test
    public void  saveTraitStateTest() throws Exception{

        TestApp testApp = TestEnvironments.random(new TestAppFilter().enableTrait());
        KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());

        String vendorThingID = "thing-" + System.currentTimeMillis();
        String password = "pa$$word";

        // registering user
        KiiNormalUser user = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
        user = rest.api().users().register(user, "password");
        rest.setCredentials(user);
        KiiThingIfResource thingif = rest.thingif();

        KiiThing thing = thingif.onboard(vendorThingID, password, DEFAULT_THINGTYPE, DEFAUTL_FIRMWARE_VERSION, null );
        Assert.assertNotNull(thing.getThingID());
        Assert.assertNotNull(thing.getAccessToken());

        KiiThingIfTargetStatesResource targets = new KiiThingIfTargetStatesResource(
                new KiiThingIfTargetResource(thingif, "thing:"+thing.getID()));

        TargetState stateToSave = new AirConditionerState(true, 23);
        targets.save(stateToJson(stateToSave), true);
    }
}

package com.kii.cloud;

import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiObject;

public class Test {
	public static void main(String[] args) throws Exception {
		KiiRest rest = new KiiRest("c230bead", "1ecb42576c3b98924f1ab21badd2e7ef", KiiRest.Site.JP);
		
		KiiNormalUser user = new KiiNormalUser().setUsername("nori" + System.currentTimeMillis()).setDisplayName("Noriyoshi Fukuzaki").setCountry("JP");
		
		rest.api().user().register(user, "pasword");
		rest.setCredentials(user);
		
		KiiObject object = new KiiObject().set("score", 0).set("level", 1);
		rest.api().user(user).bucket("by_bucket").object().register(object);
		object.set("score", 100).set("level", 2);
		rest.api().user(user).bucket("by_bucket").object(object).update(object);
	}

}

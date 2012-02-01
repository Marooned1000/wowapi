package ca.wowapi;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.wowapi.entities.Realm;

public class RealmAPITest {
	
	static RealmAPI realmAPI;
	
	@BeforeClass
	public static void setup() throws IOException {
		realmAPI = new RealmAPI();	
	}

	@Test
	public void getRealmByNameTest() {
		Realm r = realmAPI.getRealm("Ravencrest", RealmAPI.REGION_US);
		Assert.assertNotNull(r);
		Assert.assertEquals("Ravencrest", r.getName());
	}

	@Test
	public void getRealmListTest() {
		List<Realm> realms = realmAPI.getRealmList(RealmAPI.REGION_US);
		Assert.assertNotNull(realms);
		Assert.assertTrue(realms.size() > 1);
	}

	@Test
	public void getRealmNameListTest() {
		List<String> realmNames = realmAPI.getRealmNamesList(RealmAPI.REGION_US);
		Assert.assertNotNull(realmNames);
		Assert.assertTrue(realmNames.size() > 1);
	}
}

package ca.wowapi;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.wowapi.entities.Item;

public class ItemAPITest {

	private static final String[] TEST_ITEMS = { "52231", "10546", "11134", "70156", "10393", "2772", "52987", "38955", "63122", "3609", "42970", "41367", "4306", "27854", "41104", "52555", "8173",
			"12808", "40113", "1529", "7909", "22829" };

	static ItemAPI itemAPI;

	@BeforeClass
	public static void setup() throws IOException {
		itemAPI = new ItemAPI();
	}

	@Test
	public void getItemTest() {
		for (int i = 0; i < TEST_ITEMS.length; i++) {
			Item item = itemAPI.getItem(TEST_ITEMS[i], ItemAPI.REGION_US);		
			Assert.assertNotNull(item);
			Assert.assertEquals(TEST_ITEMS[i], String.valueOf(item.getId()));
		}
	}
}

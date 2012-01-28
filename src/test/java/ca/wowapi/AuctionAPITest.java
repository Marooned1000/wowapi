package ca.wowapi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuctionAPITest {

	private static final String TEST_REALM = "Ravencrest";
	private static final String TEST_FACTION = "alliance";

	static AuctionAPI auctionAPI;

	@BeforeClass
	public static void setup() throws IOException {
		auctionAPI = new AuctionAPI();
	}

	@Test
	public void getAuctionUrlTest() {
		String url = auctionAPI.getAuctionUrl(TEST_REALM, AuctionAPI.REGION_US);
		Assert.assertNotNull(url);
		Assert.assertTrue(url.matches(".*auction-data.*auctions.json.*"));
	}

	@Test
	public void getLastModifiedTest() {
		long lastModified = auctionAPI.getAuctionLastModified(TEST_REALM, AuctionAPI.REGION_US);
		Assert.assertTrue(lastModified > 0);

		Date lastModifiedDate = new Date(lastModified);
		Date nowDate = new Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Assert.assertTrue(sdf.format(lastModifiedDate).equals(sdf.format(nowDate)));
	}

	@Test
	public void getAllAuctionDataTest() {
		HashMap<String, List<AuctionItem>> auctionData = auctionAPI.getAllAuctionData(TEST_REALM, AuctionAPI.REGION_US);
		Assert.assertNotNull(auctionData.get(TEST_FACTION));
		Assert.assertNotNull(auctionData.get(TEST_FACTION).get(0));
	}

}

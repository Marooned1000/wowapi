package ca.wowapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class AuctionAPI extends AbstractAPI {

	public static final String AUCTION_API_URL = "http://%region.battle.net/api/wow/auction/data/%realm";

	public static final String[] FACTIONS = { "alliance", "horde", "neutral" };

	public AuctionAPI() {

	}

	public AuctionAPI(String publicKey, String privateKey) {
		super(publicKey, privateKey);
	}

	public String getAuctionUrl(String realm, String region) {
		String finalURL = AUCTION_API_URL.replace("%region", region).replace("%realm", realm);

		String auctionUrl = null;
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL);
			auctionUrl = jsonobject.getJSONArray("files").getJSONObject(0).getString("url");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return auctionUrl;
	}

	public long getAuctionLastModified(String realm, String region) {
		String finalURL = AUCTION_API_URL.replace("%region", region).replace("%realm", realm);

		long lastModified = 0;
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL);
			lastModified = Long.parseLong(jsonobject.getJSONArray("files").getJSONObject(0).getString("lastModified"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lastModified;
	}

	public HashMap<String, List<AuctionItem>> getAllAuctionData(String realm, String region) {
		HashMap<String, List<AuctionItem>> auctionData = null;
		try {
			String auctionUrl = this.getAuctionUrl(realm, region);
			JSONObject jsonobject = getJSONFromRequest(auctionUrl);

			auctionData = new HashMap<String, List<AuctionItem>>();
			for (int i = 0; i < FACTIONS.length; i++) {
				JSONArray jAuctionList = jsonobject.getJSONObject(FACTIONS[i]).getJSONArray("auctions");

				ArrayList<AuctionItem> auctionItemList = new ArrayList<AuctionItem>();
				for (int j = 0; j < jAuctionList.length(); j++) {
					AuctionItem auctionItem = new AuctionItem();
					auctionItem.owner = jAuctionList.getJSONObject(j).getString("owner");
					auctionItem.bid = jAuctionList.getJSONObject(j).getLong("bid");
					auctionItem.id = jAuctionList.getJSONObject(j).getLong("auc");
					auctionItem.item = jAuctionList.getJSONObject(j).getInt("item");
					auctionItem.buyout = jAuctionList.getJSONObject(j).getLong("buyout");
					auctionItem.quantity = jAuctionList.getJSONObject(j).getInt("quantity");
					auctionItem.timeLeft = jAuctionList.getJSONObject(j).getString("timeLeft");
					auctionItemList.add(auctionItem);
				}
				auctionData.put(FACTIONS[i], auctionItemList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auctionData;
	}
}

class AuctionItem {
	public long bid;
	public long buyout;
	public long id;
	public int item;
	public String owner;
	public int quantity;
	public String timeLeft;

	public String toString() {
		return id + ", " + item + ", \t" + owner + ", \t" + bid / 10000 + ", \t" + buyout / 10000 + ", \t" + quantity + ", \t" + timeLeft;
	}
}

package ca.wowapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.wowapi.entities.Auction;

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

	public HashMap<String, List<Auction>> getAllAuctionData(String realm, String region) {
		HashMap<String, List<Auction>> auctionData = null;
		try {
			String auctionUrl = this.getAuctionUrl(realm, region);
			JSONObject jsonobject = getJSONFromRequest(auctionUrl);

			auctionData = new HashMap<String, List<Auction>>();
			for (int i = 0; i < FACTIONS.length; i++) {
				JSONArray jAuctionList = jsonobject.getJSONObject(FACTIONS[i]).getJSONArray("auctions");

				ArrayList<Auction> auctionList = new ArrayList<Auction>();
				for (int j = 0; j < jAuctionList.length(); j++) {
					Auction auctionItem = new Auction();
					auctionItem.setOwner(jAuctionList.getJSONObject(j).getString("owner"));
					auctionItem.setBid(jAuctionList.getJSONObject(j).getLong("bid"));
					auctionItem.setId(jAuctionList.getJSONObject(j).getLong("auc"));
					auctionItem.setItem(jAuctionList.getJSONObject(j).getInt("item"));
					auctionItem.setBuyout(jAuctionList.getJSONObject(j).getLong("buyout"));
					auctionItem.setQuantity(jAuctionList.getJSONObject(j).getInt("quantity"));
					auctionItem.setTimeLeft(jAuctionList.getJSONObject(j).getString("timeLeft"));
					auctionList.add(auctionItem);
				}
				auctionData.put(FACTIONS[i], auctionList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auctionData;
	}

}

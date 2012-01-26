package ca.wowapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.wowapi.entities.Achievement;
import ca.wowapi.entities.Guild;
import ca.wowapi.entities.Realm;
import ca.wowapi.exceptions.CharacterNotFoundException;
import ca.wowapi.exceptions.GuildNotFoundException;
import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.NotModifiedException;
import ca.wowapi.exceptions.ServerUnavailableException;
import ca.wowapi.exceptions.TooManyRequestsException;
import ca.wowapi.utils.APIConnection;

class AuctionItem {
	public long id;
	public int item;
	public String owner;
	public long bid;
	public long buyout;
	public int quantity;
	public String timeLeft;
	
	public String toString () {
		return id + ", " + item + ", \t" + owner + ", \t" + bid/10000 + ", \t" + buyout/10000 + ", \t" + quantity + ", \t" + timeLeft;			
	}
}

public class AuctionAPI {
	
	private String cat;
	private String mouse;
	private boolean authenticate = false;

	public boolean isAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}
	
	
	public AuctionAPI (boolean authenticate, String cat, String mouse)
	{
		this.authenticate = authenticate;
		this.cat = cat;
		this.mouse = mouse;
	}

	public AuctionAPI()
	{
		run();
	}

	private void run() {
		// TODO Auto-generated method stub
		try {
			
			ArrayList<AuctionItem> auctionItemList = (ArrayList<AuctionItem>) getAllAuctionInfo ("ravencrest","eu");
			for (int i = 0; i < auctionItemList.size(); i++)
			{
				if (auctionItemList.get(i).owner.equalsIgnoreCase("shoman"))
				{
					System.out.println(auctionItemList.get(i));
				}
				
				if (auctionItemList.get(i).item == 56537)
				{
					//System.out.println(auctionItemList.get(i));
				}
			}
		
			
		} catch (ServerUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GuildNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidApplicationSignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyRequestsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotModifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject getJSONFromRequest (String url) throws NotModifiedException 
	{	
		JSONObject jsonobject;

		String str = null;
		if (authenticate)
			str = APIConnection.getStringJSONFromRequestAuth(url, cat, mouse,0);
		else		
			str = APIConnection.getStringJSONFromRequest(url,0);

		try {
			jsonobject = new JSONObject(str);
			return jsonobject;
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}


	public List<AuctionItem> getAllAuctionInfo (String realm, String region) throws ServerUnavailableException, GuildNotFoundException, InvalidApplicationSignatureException, TooManyRequestsException, NotModifiedException
	{
		String URL = "http://%region.battle.net/auction-data/%realm/auctions.json";
		
		String finalURL = URL.replace("%region", region).replace("%realm", realm);
		Guild guild = new Guild();	
		ArrayList<AuctionItem> auctionItemList = new ArrayList<AuctionItem>();
		
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL);
			JSONArray jAuctionList;

			if (jsonobject == null) throw new ServerUnavailableException();
			try {
				if (jsonobject.getString ("status").equalsIgnoreCase("nok"))
				{
					if (jsonobject.getString ("reason").equalsIgnoreCase("Character not found."))
					{
						throw new GuildNotFoundException();
					} else if (jsonobject.getString ("reason").equalsIgnoreCase("Invalid application signature."))
					{
						throw new InvalidApplicationSignatureException();
					} else if (jsonobject.getString ("reason").contains("too many requests") || jsonobject.getString ("reason").contains("Daily limit exceeded"))
					{
						throw new TooManyRequestsException();
					} else {
						throw new ServerUnavailableException();
					}
				}
			} catch (JSONException e) {};
					
			jAuctionList = jsonobject.getJSONObject("alliance").getJSONArray("auctions");
			for (int i = 0; i < jAuctionList.length();i++)
			{
				AuctionItem auctionItem = new AuctionItem();
				auctionItem.owner = jAuctionList.getJSONObject(i).getString("owner");
				auctionItem.bid = jAuctionList.getJSONObject(i).getLong("bid");
				auctionItem.id = jAuctionList.getJSONObject(i).getLong("auc");
				auctionItem.item = jAuctionList.getJSONObject(i).getInt("item");
				auctionItem.buyout = jAuctionList.getJSONObject(i).getLong("buyout");
				auctionItem.quantity = jAuctionList.getJSONObject(i).getInt("quantity");
				auctionItem.timeLeft = jAuctionList.getJSONObject(i).getString("timeLeft");
				auctionItemList.add(auctionItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return auctionItemList;
	}

	

	public static void main (String[] Argv)
	{
		new AuctionAPI();
	}
}

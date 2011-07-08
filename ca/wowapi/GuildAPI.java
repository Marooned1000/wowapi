package ca.wowapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.wowapi.entities.Guild;
import ca.wowapi.entities.Realm;
import ca.wowapi.exceptions.CharacterNotFoundException;
import ca.wowapi.exceptions.GuildNotFoundException;
import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.ServerUnavailableException;
import ca.wowapi.utils.APIConnection;

public class GuildAPI {


	String URL = "http://%region.battle.net/api/wow/guild/%realm/%name?fields=achievements";
	
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
	
	
	public GuildAPI (boolean authenticate, String cat, String mouse)
	{
		this.authenticate = authenticate;
		this.cat = cat;
		this.mouse = mouse;
	}

	public GuildAPI()
	{
	}

	public JSONObject getJSONFromRequest (String url) 
	{	
		JSONObject jsonobject;

		String str = null;
		if (authenticate)
			str = APIConnection.getStringJSONFromRequestAuth(url, cat, mouse);
		else		
			str = APIConnection.getStringJSONFromRequest(url);

		try {
			jsonobject = new JSONObject(str);
			return jsonobject;
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}


	public Guild getGuildAllInfo (String name, String realm, String region) throws ServerUnavailableException, GuildNotFoundException, InvalidApplicationSignatureException
	{
		try {
			name = URIUtil.encodePath(name,"UTF-8");
			realm = URIUtil.encodePath(realm,"UTF-8");
		} catch (URIException e) {
			e.printStackTrace();
		}		
		
		String finalURL = URL.replace("%region", region).replace("%realm", realm).replace("%name", name);
		Guild guild = new Guild();		
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL);
			JSONArray jarrayAchievementsCompleted,jarrayAchievementsCompletedTimestamp,jarrayCriteria,jarrayCriteriaQuantity,jarrayCriteriaTimestamp;

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
					} else {
						throw new GuildNotFoundException();
					}
				}
			} catch (JSONException e) {};
			
			guild.setName(jsonobject.getString("name"));
			guild.setRealm(jsonobject.getString("realm"));
			guild.setRegion(region);
			guild.setLevel(jsonobject.getInt("level"));
			guild.setPoints(jsonobject.getInt("achievementPoints"));
			
			jarrayAchievementsCompleted = jsonobject.getJSONObject("achievements").getJSONArray("achievementsCompleted");
			jarrayAchievementsCompletedTimestamp = jsonobject.getJSONObject("achievements").getJSONArray("achievementsCompletedTimestamp");
			jarrayCriteria = jsonobject.getJSONObject("achievements").getJSONArray("criteria");
			jarrayCriteriaQuantity = jsonobject.getJSONObject("achievements").getJSONArray("criteriaQuantity");
			jarrayCriteriaTimestamp = jsonobject.getJSONObject("achievements").getJSONArray("criteriaTimestamp");
			
			List<Achievement> achievementList = new ArrayList<Achievement>(); 
			
			for (int i = 0; i < jarrayAchievementsCompleted.length();i++)
			{
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayAchievementsCompleted.getInt(i));
				achievemenet.setTimestamp(jarrayAchievementsCompletedTimestamp.getLong(i));
				achievemenet.setCompleted(true);
				achievementList.add(achievemenet);
			}
			
			for (int i = 0; i < jarrayCriteria.length();i++)
			{
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayCriteria.getInt(i));
				achievemenet.setTimestamp(jarrayCriteriaTimestamp.getLong(i));
				achievemenet.setCriteriaQuantity(jarrayCriteriaQuantity.getInt(i));
				achievemenet.setCompleted(false);
				achievementList.add(achievemenet);
			}
			
			guild.setAchievements(achievementList);
			
			return guild;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}

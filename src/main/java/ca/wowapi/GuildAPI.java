package ca.wowapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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

public class GuildAPI {

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

	public JSONObject getJSONFromRequest (String url, long lastModified) throws NotModifiedException 
	{	
		JSONObject jsonobject;

		String str = null;
		if (authenticate)
			str = APIConnection.getStringJSONFromRequestAuth(url, cat, mouse, lastModified);
		else		
			str = APIConnection.getStringJSONFromRequest(url, lastModified);

		try {
			jsonobject = new JSONObject(str);
			return jsonobject;
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}


	public Guild getGuildAllInfo (String name, String realm, String region, long lastModified) throws ServerUnavailableException, GuildNotFoundException, InvalidApplicationSignatureException, TooManyRequestsException, NotModifiedException
	{
		String URL = "http://%region.battle.net/api/wow/guild/%realm/%name?fields=achievements";

		try {
			name = java.net.URLEncoder.encode(name,"UTF-8").replace("+", "%20");
			realm = java.net.URLEncoder.encode(realm,"UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String finalURL = URL.replace("%region", region).replace("%realm", realm).replace("%name", name);
		Guild guild = new Guild();		
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL, lastModified);
			JSONArray jarrayAchievementsCompleted,jarrayAchievementsCompletedTimestamp,jarrayCriteria,jarrayCriteriaQuantity,jarrayCriteriaTimestamp;

			if (jsonobject == null) throw new ServerUnavailableException();
			try {
				if (jsonobject.getString ("status").equalsIgnoreCase("nok"))
				{
					if (jsonobject.getString ("reason").equalsIgnoreCase("Guild not found."))
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

			guild.setName(jsonobject.getString("name"));
			guild.setRealm(jsonobject.getString("realm"));
			guild.setRegion(region);
			guild.setLevel(jsonobject.getInt("level"));
			guild.setPoints(jsonobject.getInt("achievementPoints"));
			guild.setLastmodified(new java.sql.Timestamp(jsonobject.getLong("lastModified")));

			if (jsonobject.getInt("side") == 0) guild.setFaction("Alliance"); 
			else if (jsonobject.getInt("side") == 1) guild.setFaction("Horde"); 

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
				achievemenet.setCriteriaQuantity(1);
				achievementList.add(achievemenet);
			}
			guild.setAchievements(achievementList);

			achievementList = new ArrayList<Achievement>(); 
			for (int i = 0; i < jarrayCriteria.length();i++)
			{
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayCriteria.getInt(i));
				achievemenet.setTimestamp(jarrayCriteriaTimestamp.getLong(i));
				achievemenet.setCriteriaQuantity(jarrayCriteriaQuantity.getInt(i));
				achievemenet.setCompleted(false);
				achievementList.add(achievemenet);
			}
			guild.setCriteria(achievementList);

			return guild;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Guild getGuildBasicInfo (String name, String realm, String region, long lastModified) throws ServerUnavailableException, GuildNotFoundException, InvalidApplicationSignatureException, TooManyRequestsException, NotModifiedException
	{
		String URL = "http://%region.battle.net/api/wow/guild/%realm/%name";
		try {
			name = java.net.URLEncoder.encode(name,"UTF-8").replace("+", "%20");
			realm = java.net.URLEncoder.encode(realm,"UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}	

		String finalURL = URL.replace("%region", region).replace("%realm", realm).replace("%name", name);
		Guild guild = new Guild();		
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL, lastModified);

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

			guild.setName(jsonobject.getString("name"));
			guild.setRealm(jsonobject.getString("realm"));
			guild.setRegion(region);
			guild.setLevel(jsonobject.getInt("level"));
			guild.setPoints(jsonobject.getInt("achievementPoints"));

			if (jsonobject.getInt("side") == 0) guild.setFaction("Alliance"); 
			else if (jsonobject.getInt("side") == 1) guild.setFaction("Horde"); 

			return guild;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}

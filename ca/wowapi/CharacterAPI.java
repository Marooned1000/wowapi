package ca.wowapi;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wowgearup.miner.utils.Constants;

import ca.wowapi.entities.Achievement;
import ca.wowapi.entities.Character;
import ca.wowapi.entities.Realm;
import ca.wowapi.exceptions.CharacterNotFoundException;
import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.NotModifiedException;
import ca.wowapi.exceptions.ServerUnavailableException;
import ca.wowapi.exceptions.TooManyRequestsException;
import ca.wowapi.utils.APIConnection;

public class CharacterAPI {

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
	
	public CharacterAPI()
	{
	}
	
	public CharacterAPI (boolean authenticate, String cat, String mouse)
	{
		this.authenticate = authenticate;
		this.cat = cat;
		this.mouse = mouse;
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
			e.printStackTrace();
			return null;
		}
	}
	



	public Character getCharacterAllInfo (String name, String realm, String region, long lastModified) throws CharacterNotFoundException, ServerUnavailableException, InvalidApplicationSignatureException, TooManyRequestsException, NotModifiedException
	{
		String URL = "http://%region.battle.net/api/wow/character/%realm/%name?fields=items,guild,achievements";
		
		Map <Integer, String> classList = new HashMap<Integer, String>();
		classList.put (11, "Druid");
		classList.put (7, "Shaman");
		classList.put (2, "Paladin");
		classList.put (6, "Death Knight");
		classList.put (4, "Rogue");
		classList.put (5, "Priest");
		classList.put (8, "Mage");
		classList.put (1, "Warrior");
		classList.put (9, "Warlock");
		classList.put (3, "Hunter");

		Map <Integer, String> raceList = new HashMap<Integer, String>();
		raceList.put (4, "Night Elf");
		raceList.put (11, "Draenei");
		raceList.put (1, "Human");
		raceList.put (3, "Dwarf");
		raceList.put (6, "Tauren");
		raceList.put (10, "Blood Elf");
		raceList.put (22, "Worgen");
		raceList.put (7, "Gnome");
		raceList.put (9, "Goblin");
		raceList.put (2, "Orc");
		raceList.put (8, "Troll");
		raceList.put (5, "Undead");

		Map <Integer, String> genderList = new HashMap<Integer, String>();
		genderList.put (0, "Male");
		genderList.put (1, "Female");



		try {
			name = URIUtil.encodePath(name,"UTF-8");
			realm = URIUtil.encodePath(realm,"UTF-8");
		} catch (URIException e) {
			e.printStackTrace();
		}	

		String finalURL = URL.replace("%region", region).replace("%realm", realm).replace("%name", name);
		Character character = new Character();
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL, lastModified);
			JSONArray jarrayAchievementsCompleted,jarrayAchievementsCompletedTimestamp,jarrayCriteria,jarrayCriteriaQuantity,jarrayCriteriaTimestamp;
			if (jsonobject == null) throw new ServerUnavailableException();
			try {
				if (jsonobject.getString ("status").equalsIgnoreCase("nok"))
				{
					if (jsonobject.getString ("reason").equalsIgnoreCase("Character not found."))
					{
						throw new CharacterNotFoundException();
					} else if (jsonobject.getString ("reason").equalsIgnoreCase("Invalid application signature."))
					{
						throw new InvalidApplicationSignatureException();
					}  else if (jsonobject.getString ("reason").contains("too many requests") || jsonobject.getString ("reason").contains("Daily limit exceeded"))
					{
						throw new TooManyRequestsException();
					} else {
						throw new ServerUnavailableException();
					}
				}
			} catch (JSONException e) {};
			
			if (region.equalsIgnoreCase("us"))
				Constants.numberOfAllCharacterRequestsUS++;
			else if (region.equalsIgnoreCase("eu"))
				Constants.numberOfAllCharacterRequestsEU++;

			character.setName(jsonobject.getString("name"));
			character.setRealm(jsonobject.getString("realm"));
			character.setRegion(region);
			character.setCclass(classList.get(jsonobject.getInt("class")));
			character.setRace(raceList.get(jsonobject.getInt("race")));
			character.setLevel(jsonobject.getInt("level"));
			character.setPoints(jsonobject.getInt("achievementPoints"));
			character.setIlvl(jsonobject.getJSONObject("items").getInt("averageItemLevel"));
			character.setGender(genderList.get(jsonobject.getInt("gender")));
			try {
				character.setGuildname(jsonobject.getJSONObject("guild").getString("name"));
			} catch (JSONException e) {
				character.setGuildname("");
			}
			
			if (jsonobject.getInt("race") == 4 || jsonobject.getInt("race") == 11 || jsonobject.getInt("race") == 1 || jsonobject.getInt("race") == 3 || jsonobject.getInt("race") == 7 || jsonobject.getInt("race") == 22)
				character.setFaction("Alliance");
			else if (jsonobject.getInt("race") == 6|| jsonobject.getInt("race") == 10 || jsonobject.getInt("race") == 9 || jsonobject.getInt("race") == 2 || jsonobject.getInt("race") == 5 || jsonobject.getInt("race") == 8)
				character.setFaction("Horde");
			else 
				character.setFaction("");


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
			character.setAchievements(achievementList);

			achievementList = new ArrayList<Achievement>();
			for (int i = 0; i < jarrayCriteria.length();i++)
			{
				/*
				Achievement achievemenet = null;
				boolean isOld = false;
				for (int z = 0; z < jarrayAchievementsCompleted.length();z++)
				{ 
					if (achievementList.get(z).getAid() == jarrayCriteria.getInt(i)) 
					{
						achievemenet = achievementList.get(z);
						isOld = true;
						break;
					}
					achievemenet = new Achievement();
				}
				if (isOld) {
					System.out.println(achievemenet.getAid());
					System.out.println(achievemenet.getTimestamp());
					System.out.println(jarrayCriteriaTimestamp.getLong(i));
					System.out.println(jarrayCriteriaQuantity.getInt(i));
				}
				*/
				
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayCriteria.getInt(i));
				achievemenet.setTimestamp(jarrayCriteriaTimestamp.getLong(i));
				achievemenet.setCriteriaQuantity(jarrayCriteriaQuantity.getLong(i));
				achievemenet.setCompleted(false);
				achievementList.add(achievemenet);
				
				//System.out.println(jarrayCriteria.getInt(i) + ":" + jarrayCriteriaQuantity.getInt(i));

				/*
				try {
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("crsader-criteria.txt",true));
					bufferedWriter.write("ID: " + jarrayCriteria.getInt(i) + ", criteria: " + jarrayCriteriaQuantity.getInt(i) + ", datetime: " + new java.util.Date(jarrayCriteriaTimestamp.getLong(i)));
					bufferedWriter.newLine();
					bufferedWriter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				System.out.println("ID: " + jarrayCriteria.getInt(i) + ", criteria: " + jarrayCriteriaQuantity.getInt(i) + ", datetime: " + new java.util.Date(jarrayCriteriaTimestamp.getLong(i)));
				 */
			}

			character.setCriteria(achievementList);
			return character;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Character getCharacterBasicInfo (String name, String realm, String region, long lastModified) throws CharacterNotFoundException, ServerUnavailableException, InvalidApplicationSignatureException, TooManyRequestsException, NotModifiedException
	{
		
		String URL = "http://%region.battle.net/api/wow/character/%realm/%name";

		Map <Integer, String> classList = new HashMap<Integer, String>();
		classList.put (11, "Druid");
		classList.put (7, "Shaman");
		classList.put (2, "Paladin");
		classList.put (6, "Death Knight");
		classList.put (4, "Rogue");
		classList.put (5, "Priest");
		classList.put (8, "Mage");
		classList.put (1, "Warrior");
		classList.put (9, "Warlock");
		classList.put (3, "Hunter");

		Map <Integer, String> raceList = new HashMap<Integer, String>();
		raceList.put (4, "Night Elf");
		raceList.put (11, "Draenei");
		raceList.put (1, "Human");
		raceList.put (3, "Dwarf");
		raceList.put (6, "Tauren");
		raceList.put (10, "Blood Elf");
		raceList.put (22, "Worgen");
		raceList.put (7, "Gnome");
		raceList.put (9, "Goblin");
		raceList.put (2, "Orc");
		raceList.put (8, "Troll");
		raceList.put (5, "Undead");

		Map <Integer, String> genderList = new HashMap<Integer, String>();
		genderList.put (0, "Male");
		genderList.put (1, "Female");



		try {
			name = URIUtil.encodePath(name,"UTF-8");
			realm = URIUtil.encodePath(realm,"UTF-8");
		} catch (URIException e) {
			e.printStackTrace();
		}	

		String finalURL = URL.replace("%region", region).replace("%realm", realm).replace("%name", name);
		Character character = new Character();
		try {
			JSONObject jsonobject = getJSONFromRequest (finalURL, lastModified);
			if (jsonobject == null) throw new ServerUnavailableException();
			try {
				if (jsonobject.getString ("status").equalsIgnoreCase("nok"))
				{
					if (jsonobject.getString ("reason").equalsIgnoreCase("Character not found."))
					{
						throw new CharacterNotFoundException();
					} else if (jsonobject.getString ("reason").equalsIgnoreCase("Invalid application signature."))
					{
						throw new InvalidApplicationSignatureException();
					}  else if (jsonobject.getString ("reason").contains("too many requests") || jsonobject.getString ("reason").contains("Daily limit exceeded"))
					{
						throw new TooManyRequestsException();
					} else {
						throw new ServerUnavailableException();
					}
				}
			} catch (JSONException e) {};
			
			if (region.equalsIgnoreCase("us"))
				Constants.numberOfBasicCharacterRequestsUS++;
			else if (region.equalsIgnoreCase("eu"))
				Constants.numberOfBasicCharacterRequestsEU++;

			character.setName(jsonobject.getString("name"));
			character.setRealm(jsonobject.getString("realm"));
			character.setRegion(region);
			character.setCclass(classList.get(jsonobject.getInt("class")));
			character.setRace(raceList.get(jsonobject.getInt("race")));
			character.setLevel(jsonobject.getInt("level"));
			character.setPoints(jsonobject.getInt("achievementPoints"));
			character.setGender(genderList.get(jsonobject.getInt("gender")));
			try {
				character.setGuildname(jsonobject.getJSONObject("guild").getString("name"));
			} catch (JSONException e) {
				character.setGuildname("");
			}
			
			if (jsonobject.getInt("race") == 4 || jsonobject.getInt("race") == 11 || jsonobject.getInt("race") == 1 || jsonobject.getInt("race") == 3 || jsonobject.getInt("race") == 7 || jsonobject.getInt("race") == 22)
				character.setFaction("Alliance");
			else if (jsonobject.getInt("race") == 6|| jsonobject.getInt("race") == 10 || jsonobject.getInt("race") == 9 || jsonobject.getInt("race") == 2 || jsonobject.getInt("race") == 5 || jsonobject.getInt("race") == 8)
				character.setFaction("Horde");
			else 
				character.setFaction("");

			return character;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}

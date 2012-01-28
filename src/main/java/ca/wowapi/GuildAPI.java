package ca.wowapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.wowapi.entities.Achievement;
import ca.wowapi.entities.Guild;

public class GuildAPI extends AbstractAPI {

	public static final String GUILD_API_URL = "http://%region.battle.net/api/wow/guild/%realm/%name";

	public GuildAPI() {

	}

	public GuildAPI(String publicKey, String privateKey) {
		super(publicKey, privateKey);
	}

	public Guild getGuildAllInfo(String name, String realm, String region) {
		return this.getGuildAllInfo(name, realm, region, 0);
	}

	public Guild getGuildAllInfo(String name, String realm, String region, long lastModified) {
		Guild guild = null;

		String URL = GUILD_API_URL + "?fields=achievements";
		String finalURL = URL.replace("%region", region).replace("%realm", encode(realm)).replace("%name", encode(name));
		try {
			guild = this.getGuildBasicInfo(name, realm, region, lastModified);

			JSONObject jsonobject = getJSONFromRequest(finalURL, lastModified);
			JSONArray jarrayAchievementsCompleted = jsonobject.getJSONObject("achievements").getJSONArray("achievementsCompleted");
			JSONArray jarrayAchievementsCompletedTimestamp = jsonobject.getJSONObject("achievements").getJSONArray("achievementsCompletedTimestamp");
			JSONArray jarrayCriteria = jsonobject.getJSONObject("achievements").getJSONArray("criteria");
			JSONArray jarrayCriteriaQuantity = jsonobject.getJSONObject("achievements").getJSONArray("criteriaQuantity");
			JSONArray jarrayCriteriaTimestamp = jsonobject.getJSONObject("achievements").getJSONArray("criteriaTimestamp");

			List<Achievement> achievementList = new ArrayList<Achievement>();
			for (int i = 0; i < jarrayAchievementsCompleted.length(); i++) {
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayAchievementsCompleted.getInt(i));
				achievemenet.setTimestamp(jarrayAchievementsCompletedTimestamp.getLong(i));
				achievemenet.setCompleted(true);
				achievemenet.setCriteriaQuantity(1);
				achievementList.add(achievemenet);
			}
			guild.setAchievements(achievementList);

			achievementList = new ArrayList<Achievement>();
			for (int i = 0; i < jarrayCriteria.length(); i++) {
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayCriteria.getInt(i));
				achievemenet.setTimestamp(jarrayCriteriaTimestamp.getLong(i));
				achievemenet.setCriteriaQuantity(jarrayCriteriaQuantity.getInt(i));
				achievemenet.setCompleted(false);
				achievementList.add(achievemenet);
			}
			guild.setCriteria(achievementList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return guild;
	}

	public Guild getGuildBasicInfo(String name, String realm, String region) {
		return this.getGuildBasicInfo(name, realm, region, 0);
	}

	public Guild getGuildBasicInfo(String name, String realm, String region, long lastModified) {
		Guild guild = null;

		String finalURL = GUILD_API_URL.replace("%region", region).replace("%realm", encode(realm)).replace("%name", encode(name));
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL, lastModified);

			guild = new Guild();
			guild.setName(jsonobject.getString("name"));
			guild.setRealm(jsonobject.getString("realm"));
			guild.setRegion(region);
			guild.setLevel(jsonobject.getInt("level"));
			guild.setPoints(jsonobject.getInt("achievementPoints"));

			if (jsonobject.getInt("side") == 0) {
				guild.setFaction("Alliance");
			} else if (jsonobject.getInt("side") == 1) {
				guild.setFaction("Horde");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return guild;
	}

}

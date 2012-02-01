package ca.wowapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.wowapi.entities.Achievement;
import ca.wowapi.entities.Character;

public class CharacterAPI extends AbstractAPI {

	private static final Map<Integer, String> CLASS_LIST;
	private static final Map<Integer, String> RACE_LIST;
	private static final Map<Integer, String> GENDER_LIST;
	static {
		CLASS_LIST = new HashMap<Integer, String>();
		CLASS_LIST.put(1, "Warrior");
		CLASS_LIST.put(2, "Paladin");
		CLASS_LIST.put(3, "Hunter");
		CLASS_LIST.put(4, "Rogue");
		CLASS_LIST.put(5, "Priest");
		CLASS_LIST.put(6, "Death Knight");
		CLASS_LIST.put(7, "Shaman");
		CLASS_LIST.put(8, "Mage");
		CLASS_LIST.put(9, "Warlock");
		CLASS_LIST.put(11, "Druid");

		RACE_LIST = new HashMap<Integer, String>();
		RACE_LIST.put(1, "Human");
		RACE_LIST.put(2, "Orc");
		RACE_LIST.put(3, "Dwarf");
		RACE_LIST.put(4, "Night Elf");
		RACE_LIST.put(5, "Undead");
		RACE_LIST.put(6, "Tauren");
		RACE_LIST.put(7, "Gnome");
		RACE_LIST.put(8, "Troll");
		RACE_LIST.put(9, "Goblin");
		RACE_LIST.put(10, "Blood Elf");
		RACE_LIST.put(11, "Draenei");
		RACE_LIST.put(22, "Worgen");

		GENDER_LIST = new HashMap<Integer, String>();
		GENDER_LIST.put(0, "Male");
		GENDER_LIST.put(1, "Female");
	}

	public static final String CHARACTER_API_URL = "http://%region.battle.net/api/wow/character/%realm/%name";

	public CharacterAPI() {
	}

	public CharacterAPI(String publicKey, String privateKey) {
		super(publicKey, privateKey);
	}

	public Character getCharacterAllInfo(String name, String realm, String region) {
		return this.getCharacterAllInfo(name, realm, region, 0);
	}

	public Character getCharacterAllInfo(String name, String realm, String region, long lastModified) {
		Character character = null;

		String URL = CHARACTER_API_URL + "?fields=items,guild,achievements";
		String finalURL = URL.replace("%region", region).replace("%realm", encode(realm)).replace("%name", encode(name));
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL, lastModified);

			character = this.getCharacterBasicInfo(name, realm, region);
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
			character.setAchievements(achievementList);

			achievementList = new ArrayList<Achievement>();
			for (int i = 0; i < jarrayCriteria.length(); i++) {
				Achievement achievemenet = new Achievement();
				achievemenet.setAid(jarrayCriteria.getInt(i));
				achievemenet.setTimestamp(jarrayCriteriaTimestamp.getLong(i));
				achievemenet.setCriteriaQuantity(jarrayCriteriaQuantity.getLong(i));
				achievemenet.setCompleted(false);
				achievementList.add(achievemenet);
			}
			character.setCriteria(achievementList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return character;
	}

	public Character getCharacterBasicInfo(String name, String realm, String region) {
		return this.getCharacterBasicInfo(name, realm, region, 0);
	}

	public Character getCharacterBasicInfo(String name, String realm, String region, long lastModified) {
		Character character = null;

		String finalURL = CHARACTER_API_URL.replace("%region", region).replace("%realm", encode(realm)).replace("%name", encode(name));
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL, lastModified);

			character = new Character();
			character.setName(jsonobject.getString("name"));
			character.setRealm(jsonobject.getString("realm"));
			character.setRegion(region);
			character.setCclass(CLASS_LIST.get(jsonobject.getInt("class")));
			character.setRace(RACE_LIST.get(jsonobject.getInt("race")));
			character.setLevel(jsonobject.getInt("level"));
			character.setPoints(jsonobject.getInt("achievementPoints"));
			character.setGender(GENDER_LIST.get(jsonobject.getInt("gender")));
			if (jsonobject.has("guild")) {
				character.setGuildName(jsonobject.getJSONObject("guild").getString("name"));
			}

			if (jsonobject.getInt("race") == 4 || jsonobject.getInt("race") == 11 || jsonobject.getInt("race") == 1 || jsonobject.getInt("race") == 3 || jsonobject.getInt("race") == 7
					|| jsonobject.getInt("race") == 22) {
				character.setFaction("Alliance");
			} else if (jsonobject.getInt("race") == 6 || jsonobject.getInt("race") == 10 || jsonobject.getInt("race") == 9 || jsonobject.getInt("race") == 2 || jsonobject.getInt("race") == 5
					|| jsonobject.getInt("race") == 8) {
				character.setFaction("Horde");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return character;
	}

}

package ca.wowapi.entities;

import java.util.List;

public class Character {

	private List<Achievement> achievements;

	private String cclass;

	private List<Achievement> criteria;

	private String faction;

	private String gender;

	private String guildName;

	private int ilvl;

	private long lastModified;

	private int level;

	private String name;

	private int points;

	private String race;

	private String realm;

	private String region;

	public Character() {
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public String getCclass() {
		return cclass;
	}

	public List<Achievement> getCriteria() {
		return criteria;
	}

	public String getFaction() {
		return faction;
	}

	public String getGender() {
		return gender;
	}

	public String getGuildName() {
		return guildName;
	}

	public int getIlvl() {
		return ilvl;
	}

	public long getLastModified() {
		return lastModified;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public String getRace() {
		return race;
	}

	public String getRealm() {
		return realm;
	}

	public String getRegion() {
		return region;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public void setCclass(String cclass) {
		this.cclass = cclass;
	}

	public void setCriteria(List<Achievement> criteria) {
		this.criteria = criteria;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setGuildName(String guildname) {
		this.guildName = guildname;
	}

	public void setIlvl(int ilvl) {
		this.ilvl = ilvl;
	}

	public void setLastModified(long lastmodified) {
		this.lastModified = lastmodified;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("name: " + name + "\n");
		output.append("realm: " + realm + "\n");
		output.append("region: " + region + "\n");
		output.append("points: " + points + "\n");
		output.append("level: " + level + "\n");
		output.append("faction: " + faction + "\n");
		output.append("cclass: " + cclass + "\n");
		output.append("faction: " + faction + "\n");
		output.append("gender: " + gender + "\n");
		output.append("guildname: " + guildName + "\n");
		output.append("ilvl: " + ilvl + "\n");
		output.append("lastmodified: " + lastModified + "\n");
		if (null != achievements) {
			output.append("achievements: " + achievements + "\n");
		}
		if (null != criteria) {
			output.append("criteria: " + criteria + "\n");
		}		
		return output.toString();
	}

}

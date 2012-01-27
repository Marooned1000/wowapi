package ca.wowapi.entities;

import java.io.Serializable;
import java.util.List;

public class Guild implements Serializable {

	private static final long serialVersionUID = 1L;

	String name;
	String realm;
	String region;
	int points;
	int level;
	String faction;
	List<Achievement> achievements;
	List<Achievement> criteria;
	java.sql.Timestamp lastmodified;

	public Guild() {
		name = "";
		realm = "";
		region = "";
		faction = "";
		points = 0;
		level = 0;
		lastmodified = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public java.sql.Timestamp getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(java.sql.Timestamp lastmodified) {
		this.lastmodified = lastmodified;
	}

	public List<Achievement> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Achievement> criteria) {
		this.criteria = criteria;
	}

	public String toString() {
		return name + "," + realm + "," + region + "," + points + "," + level + "," + faction + "," + lastmodified;
	}
}

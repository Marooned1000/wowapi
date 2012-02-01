package ca.wowapi.entities;

public class Spell {

	private String castTime;

	private String description;

	private String icon;

	private int id;

	private String name;

	public Spell() {

	}

	public String getCastTime() {
		return castTime;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setCastTime(String castTime) {
		this.castTime = castTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}

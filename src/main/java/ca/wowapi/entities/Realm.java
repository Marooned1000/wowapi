package ca.wowapi.entities;


enum RealmPopulation {
	UNKNOWN,
    LOW,
    MEDIUM,
    HIGH,
    FULL
  }

enum RealmType {
	UNKNOWN,
    PVE,
    PVP,
    RPPVE,
    RPPVP
  }

public class Realm {
	
	private boolean queue;
	public boolean isQueue() {
		return queue;
	}
	public void setQueue(boolean queue) {
		this.queue = queue;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private boolean status;
	private String name;
	private String slug;
	//private RealmPopulation population;
	//private RealmType type;
	private String population;
	private String type;
	
	public String toString() {
		return name + ", " + slug + ", " + population + ", " + type + ", " + status + ", " + queue;
	}
	
}

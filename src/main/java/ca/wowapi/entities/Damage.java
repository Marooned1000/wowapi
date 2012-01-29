package ca.wowapi.entities;

public class Damage {

	private int maxDamage;

	private int minDamage;

	public Damage() {

	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\n");
		output.append("\t\t min: " + minDamage + "\n");
		output.append("\t\t max: " + maxDamage + "\n");
		return output.toString();
	}
}

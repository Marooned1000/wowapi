package ca.wowapi.entities;

public class WeaponInfo {

	private Damage damage;

	private double dps;

	private double weaponSpeed;

	public WeaponInfo() {

	}

	public Damage getDamage() {
		return damage;
	}

	public double getDps() {
		return dps;
	}

	public double getWeaponSpeed() {
		return weaponSpeed;
	}

	public void setDamage(Damage damage) {
		this.damage = damage;
	}

	public void setDps(double dps) {
		this.dps = dps;
	}

	public void setWeaponSpeed(double weaponSpeed) {
		this.weaponSpeed = weaponSpeed;
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\n");
		if (null != damage) {
			output.append("\t damage: " + damage + "\n");
		}
		output.append("\t weaponSpeed: " + weaponSpeed + "\n");
		output.append("\t dps: " + dps + "\n");
		return output.toString();
	}
}

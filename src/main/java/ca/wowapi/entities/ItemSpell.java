package ca.wowapi.entities;

public class ItemSpell {

	private int categoryId;

	private boolean consumable;

	private int nCharges;

	private Spell spell;

	private int spellId;

	public ItemSpell() {

	}

	public int getCategoryId() {
		return categoryId;
	}

	public int getnCharges() {
		return nCharges;
	}

	public Spell getSpell() {
		return spell;
	}

	public int getSpellId() {
		return spellId;
	}

	public boolean isConsumable() {
		return consumable;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}

	public void setnCharges(int nCharges) {
		this.nCharges = nCharges;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}
}

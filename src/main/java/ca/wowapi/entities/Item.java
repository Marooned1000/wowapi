package ca.wowapi.entities;

import java.util.ArrayList;

public class Item {
	private int baseArmor;

	private BonusStats bonusStats;

	private long buyPrice;

	private int containerSlots;

	private String description;

	private int disenchantingSkillRank;

	private boolean equippable;

	private boolean hasSockets;

	private int id;

	private int inventoryType;

	private boolean isAuctionable;

	private int itemBind;

	private int itemClass;

	private int itemLevel;

	private ItemSource itemSource;

	private ArrayList<ItemSpell> itemSpells;

	private int itemSubClass;

	private int maxCount;

	private int maxDurability;

	private int minFactionId;

	private int minReputation;

	private String name;

	private int quality;

	private int requiredLevel;

	private int requiredSkill;

	private int requiredSkillRank;

	private long sellPrice;

	private int stackable;

	private WeaponInfo weaponInfo;

	public Item() {

	}

	public int getBaseArmor() {
		return baseArmor;
	}

	public BonusStats getBonusStats() {
		return bonusStats;
	}

	public long getBuyPrice() {
		return buyPrice;
	}

	public int getContainerSlots() {
		return containerSlots;
	}

	public String getDescription() {
		return description;
	}

	public int getDisenchantingSkillRank() {
		return disenchantingSkillRank;
	}

	public int getId() {
		return id;
	}

	public int getInventoryType() {
		return inventoryType;
	}

	public int getItemBind() {
		return itemBind;
	}

	public int getItemClass() {
		return itemClass;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public ItemSource getItemSource() {
		return itemSource;
	}

	public ArrayList<ItemSpell> getItemSpells() {
		return itemSpells;
	}

	public int getItemSubClass() {
		return itemSubClass;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public int getMinFactionId() {
		return minFactionId;
	}

	public int getMinReputation() {
		return minReputation;
	}

	public String getName() {
		return name;
	}

	public int getQuality() {
		return quality;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public int getRequiredSkill() {
		return requiredSkill;
	}

	public int getRequiredSkillRank() {
		return requiredSkillRank;
	}

	public long getSellPrice() {
		return sellPrice;
	}

	public WeaponInfo getWeaponInfo() {
		return weaponInfo;
	}

	public boolean isAuctionable() {
		return isAuctionable;
	}

	public boolean isEquippable() {
		return equippable;
	}

	public boolean isHasSockets() {
		return hasSockets;
	}

	public int getStackable() {
		return stackable;
	}

	public void setAuctionable(boolean isAuctionable) {
		this.isAuctionable = isAuctionable;
	}

	public void setBaseArmor(int baseArmor) {
		this.baseArmor = baseArmor;
	}

	public void setBonusStats(BonusStats bonusStats) {
		this.bonusStats = bonusStats;
	}

	public void setBuyPrice(long buyPrice) {
		this.buyPrice = buyPrice;
	}

	public void setContainerSlots(int containerSlots) {
		this.containerSlots = containerSlots;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisenchantingSkillRank(int disenchantingSkillRank) {
		this.disenchantingSkillRank = disenchantingSkillRank;
	}

	public void setEquippable(boolean equippable) {
		this.equippable = equippable;
	}

	public void setHasSockets(boolean hasSockets) {
		this.hasSockets = hasSockets;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInventoryType(int inventoryType) {
		this.inventoryType = inventoryType;
	}

	public void setItemBind(int itemBind) {
		this.itemBind = itemBind;
	}

	public void setItemClass(int itemClass) {
		this.itemClass = itemClass;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public void setItemSource(ItemSource itemSource) {
		this.itemSource = itemSource;
	}

	public void setItemSpells(ArrayList<ItemSpell> itemSpells) {
		this.itemSpells = itemSpells;
	}

	public void setItemSubClass(int itemSubClass) {
		this.itemSubClass = itemSubClass;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}

	public void setMinFactionId(int minFactionId) {
		this.minFactionId = minFactionId;
	}

	public void setMinReputation(int minReputation) {
		this.minReputation = minReputation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public void setRequiredSkill(int requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	public void setRequiredSkillRank(int requiredSkillRank) {
		this.requiredSkillRank = requiredSkillRank;
	}

	public void setSellPrice(long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public void setStackable(int stackable) {
		this.stackable = stackable;
	}

	public void setWeaponInfo(WeaponInfo weaponInfo) {
		this.weaponInfo = weaponInfo;
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("id: " + id + "\n");
		output.append("disenchangintSkillRank: " + disenchantingSkillRank + "\n");
		output.append("description: " + description + "\n");
		output.append("name: " + name + "\n");
		output.append("stackable: " + stackable + "\n");
		output.append("itemBind: " + itemBind + "\n");
		if (null != bonusStats) {
			output.append("bonusStats: " + bonusStats + "\n");
		}
		if (null != itemSpells) {
			output.append("itemSpells: " + itemSpells + "\n");
		}
		output.append("buyPrice: " + buyPrice + "\n");
		output.append("itemClass: " + itemClass + "\n");
		output.append("itemSubClass: " + itemSubClass + "\n");
		output.append("containerSlots: " + containerSlots + "\n");
		if (null != weaponInfo) {
			output.append("weaponInfo: " + weaponInfo + "\n");
		}
		output.append("inventoryType: " + inventoryType + "\n");
		output.append("equippable: " + equippable + "\n");
		output.append("itemLevel: " + itemLevel + "\n");
		output.append("maxCount: " + maxCount + "\n");
		output.append("maxDurability: " + maxDurability + "\n");
		output.append("minFactionId: " + minFactionId + "\n");
		output.append("minReputation: " + minReputation + "\n");
		output.append("quality: " + quality + "\n");
		output.append("sellPrice: " + sellPrice + "\n");
		output.append("requiredLevel: " + requiredLevel + "\n");
		output.append("requiredSkill: " + requiredSkill + "\n");
		if (null != itemSource) {
			output.append("itemSource: " + itemSource + "\n");
		}
		output.append("baseArmor: " + baseArmor + "\n");
		output.append("hasSockets: " + hasSockets + "\n");
		output.append("isAuctionable: " + isAuctionable + "\n");
		return output.toString();
	}
}

package ca.wowapi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.wowapi.entities.Damage;
import ca.wowapi.entities.Item;
import ca.wowapi.entities.ItemSource;
import ca.wowapi.entities.ItemSpell;
import ca.wowapi.entities.Spell;
import ca.wowapi.entities.WeaponInfo;

public class ItemAPI extends AbstractAPI {

	public static final String ITEM_API_URL = "http://%region.battle.net/api/wow/item/%id";

	public ItemAPI() {

	}

	public ItemAPI(String publicKey, String privateKey) {
		super(publicKey, privateKey);
	}

	public Item getItem(String itemId, String region) {
		Item item = null;

		String finalURL = ITEM_API_URL.replace("%region", region).replace("%id", itemId);
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL);

			item = new Item();
			item.setId(jsonobject.getInt("id"));
			if (jsonobject.has("disenchantingSkillRank")) {
				item.setDisenchantingSkillRank(jsonobject.getInt("disenchantingSkillRank"));
			}
			item.setDescription(jsonobject.getString("description"));
			item.setName(jsonobject.getString("name"));
			item.setSellPrice(jsonobject.getInt("stackable"));
			item.setItemBind(jsonobject.getInt("itemBind"));
			item.setBonusStats(null);
			if (jsonobject.has("itemSpells")) {

				ArrayList<ItemSpell> itemSpells = new ArrayList<ItemSpell>();
				JSONArray spellArray = jsonobject.getJSONArray("itemSpells");
				for (int i = 0; i < spellArray.length(); i++) {

					JSONObject itemSpellObj = spellArray.getJSONObject(i);
					ItemSpell itemSpell = new ItemSpell();
					itemSpell.setSpellId(itemSpellObj.getInt("spellId"));

					JSONObject spellObj = itemSpellObj.getJSONObject("spell");
					Spell spell = new Spell();
					spell.setId(spellObj.getInt("id"));
					spell.setName(spellObj.getString("name"));
					spell.setDescription(spellObj.getString("description"));
					spell.setCastTime(spellObj.getString("castTime"));

					itemSpell.setSpell(spell);
					itemSpell.setnCharges(itemSpellObj.getInt("nCharges"));
					itemSpell.setConsumable(itemSpellObj.getBoolean("consumable"));
					itemSpell.setCategoryId(itemSpellObj.getInt("categoryId"));

					itemSpells.add(itemSpell);
				}

				item.setItemSpells(itemSpells);
			}

			item.setItemSpells(null);
			item.setBuyPrice(jsonobject.getLong("buyPrice"));
			item.setItemClass(jsonobject.getInt("itemClass"));
			item.setItemSubClass(jsonobject.getInt("itemSubClass"));
			item.setContainerSlots(jsonobject.getInt("containerSlots"));

			if (jsonobject.has("weaponInfo")) {
				JSONObject weaponObj = jsonobject.getJSONObject("weaponInfo");
				JSONObject damageObj = weaponObj.getJSONObject("damage");

				Damage damage = new Damage();
				damage.setMinDamage(damageObj.getInt("min")); // minDamage
				damage.setMaxDamage(damageObj.getInt("max")); // maxDamage

				WeaponInfo weaponInfo = new WeaponInfo();
				weaponInfo.setDamage(damage);
				weaponInfo.setWeaponSpeed(weaponObj.getDouble("weaponSpeed"));
				weaponInfo.setDps(weaponObj.getDouble("dps"));

				item.setWeaponInfo(weaponInfo);
			}

			item.setInventoryType(jsonobject.getInt("inventoryType"));
			item.setEquippable(jsonobject.getBoolean("equippable"));
			item.setItemLevel(jsonobject.getInt("itemLevel"));
			item.setMaxCount(jsonobject.getInt("maxCount"));
			item.setMaxDurability(jsonobject.getInt("maxDurability"));
			item.setMinFactionId(jsonobject.getInt("minFactionId"));
			item.setMinReputation(jsonobject.getInt("minReputation"));
			item.setQuality(jsonobject.getInt("quality"));
			item.setSellPrice(jsonobject.getLong("sellPrice"));
			item.setRequiredLevel(jsonobject.getInt("requiredLevel"));
			item.setRequiredSkill(jsonobject.getInt("requiredSkill"));
			item.setRequiredSkillRank(jsonobject.getInt("requiredSkillRank"));

			if (jsonobject.has("itemSource")) {
				JSONObject itemSourceObj = jsonobject.getJSONObject("itemSource");

				ItemSource itemSource = new ItemSource();
				itemSource.setSourceId(itemSourceObj.getInt("sourceId"));
				itemSource.setSourceType(itemSourceObj.getString("sourceType"));
			}

			item.setBaseArmor(jsonobject.getInt("baseArmor"));
			item.setHasSockets(jsonobject.getBoolean("hasSockets"));
			item.setAuctionable(jsonobject.getBoolean("isAuctionable"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
}

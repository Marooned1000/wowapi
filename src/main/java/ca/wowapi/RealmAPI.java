package ca.wowapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.wowapi.entities.Realm;

public class RealmAPI extends AbstractAPI {

	public static final String REALM_API_URL = "http://%region.battle.net/api/wow/realm/status";

	public RealmAPI() {

	}
	
	public RealmAPI(String publicKey, String privateKey) {
		super(publicKey, privateKey);
	}

	public Realm getRealm(String name, String region) {
		String finalURL = REALM_API_URL.replace("%region", region);
		finalURL += "?realm=" + name;

		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL);
			JSONArray jarray = jsonobject.getJSONArray("realms");
			jsonobject = jarray.getJSONObject(0);

			Realm realm = new Realm();
			realm.setName(jsonobject.getString("name"));
			realm.setPopulation(jsonobject.getString("population"));
			realm.setType(jsonobject.getString("type"));
			realm.setSlug(jsonobject.getString("slug"));
			realm.setStatus(jsonobject.getBoolean("status"));
			realm.setQueue(jsonobject.getBoolean("queue"));

			return realm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Realm> getRealmList(String region) {
		String finalURL = REALM_API_URL.replace("%region", region);

		ArrayList<Realm> list = new ArrayList<Realm>();
		try {
			JSONObject jsonobject = getJSONFromRequest(finalURL);

			JSONArray jarray = jsonobject.getJSONArray("realms");
			for (int i = 0; i < jarray.length(); i++) {

				jsonobject = jarray.getJSONObject(i);

				Realm realm = new Realm();
				realm.setName(jsonobject.getString("name"));
				realm.setPopulation(jsonobject.getString("population"));
				realm.setType(jsonobject.getString("type"));
				realm.setSlug(jsonobject.getString("slug"));
				realm.setStatus(jsonobject.getBoolean("status"));
				realm.setQueue(jsonobject.getBoolean("queue"));

				list.add(realm);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getRealmNamesList(String region) {
		String finalURL = REALM_API_URL.replace("%region", region);

		ArrayList<String> names = new ArrayList<String>();
		try {

			JSONObject jsonobject = getJSONFromRequest(finalURL);
			JSONArray jarray = jsonobject.getJSONArray("realms");
			for (int i = 0; i < jarray.length(); i++) {
				jsonobject = jarray.getJSONObject(i);
				names.add(jsonobject.getString("name"));
			}

			return names;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

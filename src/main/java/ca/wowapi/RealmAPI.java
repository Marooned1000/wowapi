package ca.wowapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.wowapi.entities.Realm;

public class RealmAPI {


	String URL = "http://%region.battle.net/api/wow/realm/status";
	private String region;

	public RealmAPI()
	{
		region = "NotSet";
	}

	public RealmAPI (String _region)
	{
		region = _region;
	}

	public JSONObject getJSONFromRequest (String url) 
	{	
		JSONObject jsonobject;


		//byte[] responseBody = MyHttpClient.getPage (client, URL);
		//String str = new String (responseBody, utf8charset);

		
		String str = null;
		try {
			URL jURL = new URL(url);
			URLConnection urlConnection;
			urlConnection = jURL.openConnection();
			
			final char[] buffer = new char[0x1000];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
			int read;
			do {
			  read = in.read(buffer, 0, buffer.length);
			  if (read>0) {
			    out.append(buffer, 0, read);
			  }
			} while (read>=0);
			
			str = out.toString();
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		try {
			jsonobject = new JSONObject(str);
			return jsonobject;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Realm getRealmByName (String name)
	{
		if (region.equals("NotSet")) {
			System.out.println("Error: Region is not set");
			return null;
		}
		return getRealmByName (name, region);
	}

	public Realm getRealmByName (String name, String region)
	{
		String finalURL = URL.replace("%region", region);
		finalURL += "?realm=" + name;

		JSONObject jsonobject = getJSONFromRequest (finalURL);
		JSONArray jarray;
		Realm realm = new Realm();

		try {
			jarray = jsonobject.getJSONArray("realms");

			jsonobject = jarray.getJSONObject(0);
			realm.setName(jsonobject.getString("name"));
			realm.setPopulation(jsonobject.getString("population"));
			realm.setType(jsonobject.getString("type"));
			realm.setSlug(jsonobject.getString("slug"));
			realm.setStatus(jsonobject.getBoolean("status"));
			realm.setQueue(jsonobject.getBoolean("queue"));

			return realm;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Realm> getRealmList ()
	{
		if (region.equals("NotSet")) {
			System.out.println("Error: Region is not set");
			return null;
		}
		return getRealmList(region);
	}

	public List<Realm> getRealmList (String region)
	{
		String finalURL = URL.replace("%region", region);
		JSONObject jsonobject = getJSONFromRequest (finalURL);
		JSONArray jarray;
		ArrayList<Realm> list = new ArrayList<Realm>(); 

		try {
			jarray = jsonobject.getJSONArray("realms");

			for (int i = 0; i < jarray.length(); i++)
			{
				Realm realm = new Realm();
				jsonobject = jarray.getJSONObject(i);
				realm.setName(jsonobject.getString("name"));
				realm.setPopulation(jsonobject.getString("population"));
				realm.setType(jsonobject.getString("type"));
				realm.setSlug(jsonobject.getString("slug"));
				realm.setStatus(jsonobject.getBoolean("status"));
				realm.setQueue(jsonobject.getBoolean("queue"));

				list.add(realm);
			}
			return list;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] getRealmNamesList ()
	{
		if (region.equals("NotSet")) {
			System.out.println("Error: Region is not set");
			return null;
		}
		return getRealmNamesList(region);
	}

	public String[] getRealmNamesList (String region)
	{
		String finalURL = URL.replace("%region", region);
		JSONObject jsonobject = getJSONFromRequest (finalURL);
		JSONArray jarray;

		try {
			jarray = jsonobject.getJSONArray("realms");
			String[] res = new String[jarray.length()];
			for (int i = 0; i < jarray.length(); i++)
			{
				jsonobject = jarray.getJSONObject(i);
				res[i] = jsonobject.getString("name");
			}
			return res;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setRegion (String _region)
	{
		region = _region;
	}

	public String getRegion ()
	{
		return region;
	}

}

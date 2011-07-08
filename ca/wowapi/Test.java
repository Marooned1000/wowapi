package ca.wowapi;

// This is Not a Test Case

import ca.wowapi.entities.Character;
import ca.wowapi.entities.Guild;
import ca.wowapi.entities.Realm;
import ca.wowapi.exceptions.CharacterNotFoundException;
import ca.wowapi.exceptions.GuildNotFoundException;
import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.ServerUnavailableException;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		CharacterAPI charAPI = new CharacterAPI(true,"publickey","privatekey");
		try {
			Character character = charAPI.getCharacterAllInfo("Tme","Khadgar","us");
			System.out.println(character);
		} catch (CharacterNotFoundException e) {
			System.out.println("notfound");
		} catch (ServerUnavailableException e) {
			System.out.println("servernist");
			e.printStackTrace();
		} catch (InvalidApplicationSignatureException e) {
			System.out.println("signature wrong");
			e.printStackTrace();
		}
		
	
		
		GuildAPI guildAPI = new GuildAPI(true,"publickey","privatekey");
		try {
			Guild guild = guildAPI.getGuildAllInfo("shattered dreams","ravencrest","eu");
			System.out.println(guild);
		} catch (ServerUnavailableException e) {
			e.printStackTrace();
		} catch (GuildNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidApplicationSignatureException e) {
			System.out.println("signature wrong");
			e.printStackTrace();
		}
		
		RealmAPI realmAPI = new RealmAPI ("us");
		realmAPI.setRegion("eu");
		Realm r = realmAPI.getRealmByName("ravEncrest");
		System.out.println(r);
		List<Realm> list = realmAPI.getRealmList();
		System.out.println(list.get(5));
	}
}

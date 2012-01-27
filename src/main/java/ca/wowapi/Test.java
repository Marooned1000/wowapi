package ca.wowapi;

// This is Not a Test Case

import java.util.List;

import ca.wowapi.entities.Character;
import ca.wowapi.entities.Guild;
import ca.wowapi.entities.Realm;
import ca.wowapi.exceptions.CharacterNotFoundException;
import ca.wowapi.exceptions.GuildNotFoundException;
import ca.wowapi.exceptions.InvalidApplicationSignatureException;
import ca.wowapi.exceptions.NotModifiedException;
import ca.wowapi.exceptions.ServerUnavailableException;
import ca.wowapi.exceptions.TooManyRequestsException;

public class Test {

	public static void main(String[] args) {

		CharacterAPI charAPI = new CharacterAPI();
		try {
			Character character = charAPI.getCharacterAllInfo("Crsader", "Ravencrest", "eu", 0);
			System.out.println(character);
			for (int i = 0; i < character.getAchievements().size(); i++)
				if (character.getAchievements().get(i).getAid() == 116305)
					System.out.println(character.getAchievements().get(i).getCriteriaQuantity());

		} catch (CharacterNotFoundException e) {
			System.out.println("notfound");
		} catch (ServerUnavailableException e) {
			System.out.println("servernist");
			e.printStackTrace();
		} catch (InvalidApplicationSignatureException e) {
			System.out.println("signature wrong");
		} catch (TooManyRequestsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotModifiedException e) {
			// TODO Auto-generated catch block
			System.out.println("Not Modified");
		}

		GuildAPI guildAPI = new GuildAPI();
		try {
			Guild guild = guildAPI.getGuildAllInfo("Alterac Deviants", "ravencrest", "eu", 0);
			System.out.println(guild);
		} catch (ServerUnavailableException e) {
			e.printStackTrace();
		} catch (GuildNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidApplicationSignatureException e) {
			System.out.println("signature wrong");
		} catch (TooManyRequestsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotModifiedException e) {
			// TODO Auto-generated catch block
			System.out.println("Not Modified");
		}

		RealmAPI realmAPI = new RealmAPI("us");
		realmAPI.setRegion("eu");
		Realm r = realmAPI.getRealmByName("ravEncrest");
		System.out.println(r);
		List<Realm> list = realmAPI.getRealmList();
		System.out.println(list.get(5));

	}
}

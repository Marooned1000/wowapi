package ca.wowapi;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.wowapi.entities.Character;

public class CharacterAPITest {

	private static final String TEST_REALM = "Ravencrest";

	private static final String TEST_CHARACTER = "Crsader";

	static CharacterAPI characterAPI;

	@BeforeClass
	public static void setup() throws IOException {
		characterAPI = new CharacterAPI();
	}

	@Test
	public void getCharacterBasicInfoTest() {
		Character character = characterAPI.getCharacterBasicInfo(TEST_CHARACTER, TEST_REALM, CharacterAPI.REGION_EU);
		Assert.assertNotNull(character);
		Assert.assertEquals(TEST_CHARACTER, character.getName());
		Assert.assertNull(character.getAchievements());
	}

	@Test
	public void getCharacterAllInfoTest() {
		Character character = characterAPI.getCharacterAllInfo(TEST_CHARACTER, TEST_REALM, CharacterAPI.REGION_EU);
		Assert.assertNotNull(character);
		Assert.assertEquals(TEST_CHARACTER, character.getName());
		Assert.assertNotNull(character.getAchievements());
	}
}

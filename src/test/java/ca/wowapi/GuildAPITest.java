package ca.wowapi;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.wowapi.entities.Guild;

public class GuildAPITest {

	private static final String TEST_REALM = "Ravencrest";

	private static final String TEST_GUILD = "Alterac Deviants";

	static GuildAPI guildAPI;

	@BeforeClass
	public static void setup() throws IOException {
		guildAPI = new GuildAPI();
	}

	@Test
	public void getGuildBasicInfoTest() {
		Guild guild = guildAPI.getGuildBasicInfo(TEST_GUILD, TEST_REALM, GuildAPI.REGION_EU);
		Assert.assertNotNull(guild);
		Assert.assertEquals(TEST_GUILD, guild.getName());
		Assert.assertNull(guild.getAchievements());
	}

	@Test
	public void getGuildAllInfoTest() {
		Guild guild = guildAPI.getGuildAllInfo(TEST_GUILD, TEST_REALM, GuildAPI.REGION_EU);
		Assert.assertNotNull(guild);
		Assert.assertEquals(TEST_GUILD, guild.getName());
		Assert.assertNotNull(guild.getAchievements());
	}
}

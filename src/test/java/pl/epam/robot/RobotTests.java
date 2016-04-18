package pl.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.epam.robot.parsing.FreeBookTagsFinder;


public class RobotTests {
	
	@Test
	public void testGettingTagsFromNexto() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder();
		String tags = fbtf.getTags("blabla", "Nexto");
		assertThat(tags).isNull();
	}
	
	@Test
	public void testGettingTagsFromPublio() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder();
		String tags = fbtf.getTags("blabla", "Publio");
		assertThat(tags).isNull();
	}
}

package pl.epam.robot.urlGenerator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

public class URLGeneratorTest {
	
	/**
	 * Test generating URLS by URLGenerator
	 */
	@Test(groups = "fastTests")
	public void testGeneratingURLs() {
		URLGenerator generator = new URLGenerator();

		List<URL> urls = generator.getUrls();

		assertThat(urls).isNotEmpty();
	}

}

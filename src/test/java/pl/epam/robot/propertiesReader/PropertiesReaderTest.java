package pl.epam.robot.propertiesReader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

public class PropertiesReaderTest {
	/**
	 * Test getting bookstores names from properties file
	 */
	@Test(groups = "fastTests")
	public void testGettingBookstoresNames() {
		PropertiesReader reader = new PropertiesReader();
		List<String> bookstoresNames = reader.getBookstoresNames();

		assertThat(bookstoresNames).isNotEmpty();
	}
}

package pl.epam.robot.propertiesReader;



import java.util.List;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesReaderTest {
	
	/**
	 * Test getting bookstores names from properties file
	 */
	@Test(groups = {"fastTests", "nonDatabaseTests"})
	public void testGettingBookstoresNames() {
		PropertiesReader reader = new PropertiesReader();
		List<String> bookstoresNames = reader.getBookstoresNames();

		assertThat(bookstoresNames).isNotEmpty();
	}
}

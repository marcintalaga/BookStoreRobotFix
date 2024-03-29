package pl.epam.robot.propertiesReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Gets bookstores names from properties and puts them into array. Then array is returned.
 * 
 * @author paulina
 * 
 *
 */
public class PropertiesReader {
	final static Logger bookslogger = Logger.getLogger("booksLogger");
	final static Logger logger = Logger.getLogger("logger");
	private static final String PROPERTIES_FILE_NAME = "URL.properties";

	private Properties properties;
	private InputStream inputStream;

	public PropertiesReader() {
		properties = new Properties();
		inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
	}

	public List<String> getBookstoresNames() {
		int nameSuffix = 0;
		List<String> bookstoresNamesList = new ArrayList<>();

		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				logger.error("Cant find 'URL.properties' file");
			}
		}

		for (int i = nameSuffix; i <= 4; i++) {
			String bookstoreName = properties.getProperty("name" + i);
			bookstoresNamesList.add(bookstoreName);
		}

		return bookstoresNamesList;
	}

}

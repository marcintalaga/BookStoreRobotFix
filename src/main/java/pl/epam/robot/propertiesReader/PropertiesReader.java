package pl.epam.robot.propertiesReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {
	final static Logger bookslogger = Logger.getLogger("booksLogger");
	final static Logger logger = Logger.getLogger("logger");
	private static final String PROPERTIES_FILE_NAME = "URL.properties";

	
	private Properties properties = new Properties();
	private InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
	

	
	public List<String> getBookstoresNames() {
		int nameSuffix = 1;
		List<String> bookstoresNamesList = new ArrayList<>();

		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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

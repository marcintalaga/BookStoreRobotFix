package pl.epam.robot.urlGenerator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Generates List<URL> based of URL prefix, suffix, starting int, step and
 * finish int (URL.properties file). If the URL step is empty in URl.properties,
 * method will generate single URL based on URL prefix only. Logging an error if
 * there is no URL.properties in resource folder
 * 
 * @author Aleksander
 */
public class URLGenerator {

	private final static Logger logger = Logger.getLogger("logger");

	private List<URL> urls;
	private Properties properties;

	/**
	 * Public constructor which initializes url list and properties
	 */
	public URLGenerator() {
		urls = new ArrayList<URL>();
		properties = new Properties();
	}

	/**
	 * A method which reads urls from properties file
	 * 
	 * @return List<URL>
	 */
	public List<URL> getUrls() {

		readFromFile();

		if (properties != null || !properties.isEmpty()) {
			generateURL();
		} else {
			logger.error("Properties file is null or empty");
		}

		return urls;
	}

	/**
	 * Method loads properties from external file URL.properties
	 */
	private void readFromFile() {
		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/URL.properties"));

		try {
			properties.load(fileReader);
		} catch (IOException e) {
			logger.error("Cant find 'URL.properties' file in resources folder");
		}
	}

	/**
	 * Method fills the list of URLs
	 */
	private void generateURL() {
		for (int i = 0; i < Integer.parseInt(properties.getProperty("NumberOfURLs")); i++) {
			urls.add(new URL());
			if (properties.getProperty("URLStep" + i).isEmpty()) {
				urls.get(urls.size() - 1).addUrl(properties.getProperty("URLPrefix" + i));
			} else {
				for (int j = Integer.parseInt(properties.getProperty("URLStart" + i)); j < Integer
						.parseInt(properties.getProperty("URLFinish" + i)); j += Integer
								.parseInt(properties.getProperty("URLStep" + i))) {
					urls.get(urls.size() - 1).addUrl(
							properties.getProperty("URLPrefix" + i) + j + properties.getProperty("URLSuffix" + i));
				}
			}
		}
	}
}

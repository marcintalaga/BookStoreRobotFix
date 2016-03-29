package pl.epam.robot.parsing;

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

	final static Logger logger = Logger.getLogger("logger");
	List<URL> urls = new ArrayList<URL>();
	Properties properties = new Properties();

	/**
	 * @return List<URL>
	 */
	public List<URL> getUrls() {
		try {
		generateUrls();
		} catch(Exception e) {
			logger.error("Incorrect number format");
		}
		return urls;
	}

	private void generateUrls() {
		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/URL.properties"));

		try {

			properties.load(fileReader);
		} catch (IOException e) {
			logger.error("Cant find 'URL.properties' file in resources folder");
		}
		for (int i = 0; i < Integer.parseInt(properties.getProperty("NumberOfURLs")); i++) {
			urls.add(new URL());
			if (properties.getProperty("URLStep" + i).isEmpty()) {
				urls.get(urls.size() - 1).addUrl(properties.getProperty("URLPrefix" + i));
			} else {
				for (int j = Integer.parseInt(properties.getProperty("URLStart" + i)); j < Integer.parseInt(properties
						.getProperty("URLFinish" + i)); j += Integer.parseInt(properties.getProperty("URLStep" + i))) {
					urls.get(urls.size() - 1).addUrl(
							properties.getProperty("URLPrefix" + i) + j + properties.getProperty("URLSuffix" + i));
				}
			}

		}
	}
}

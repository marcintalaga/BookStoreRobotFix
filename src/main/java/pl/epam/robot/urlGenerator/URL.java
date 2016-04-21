package pl.epam.robot.urlGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Value object. Contains list of URLs from single bookstore.
 * 
 * @author Aleksander
 *
 */
public class URL {
	private List<String> urls = new ArrayList<String>();
	
	
	public List<String> getUrls() {
		return urls;
	}

	public void addUrl(String url) {
		urls.add(url);
	}

}

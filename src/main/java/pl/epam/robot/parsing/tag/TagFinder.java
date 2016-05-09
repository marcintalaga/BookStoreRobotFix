package pl.epam.robot.parsing.tag;

import java.util.Map;

/**
 * Gets book title and tag from its website
 * 
 * @author paulina
 *
 */
public interface TagFinder {

	/**
	 * Gets tags from proper website
	 * 
	 * @return Map with book title and tag
	 */
	public Map<String, String> getTags();
}

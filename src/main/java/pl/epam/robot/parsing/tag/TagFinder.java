package pl.epam.robot.parsing.tag;

import java.util.Map;

/**
 * Gets book title and tag from its website
 * 
 * @author paulina
 *
 */
public interface TagFinder {

	public Map<String, String> getTags();
}

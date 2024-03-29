package pl.epam.robot.parsing.category;

import java.util.Map;

/**
 * Gets book title and categories from its website
 * @author paulina
 *
 */
public interface CategoryFinder {

	/**
	 * Gets categories in default way
	 * 
	 * @return map with title and category of book
	 */
	public Map<String, String> getCategories();
}

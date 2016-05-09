package pl.epam.robot.database.dao.category;

import pl.epam.robot.database.dao.GenericDAO;
import pl.epam.robot.database.entity.category.Category;

/**
 * Data Access Object for Category entity
 * 
 * @author paulina
 *
 */
public interface CategoryDAO extends GenericDAO<Category, Integer> {

	public Category findByCategoryType(String categoryType);
}

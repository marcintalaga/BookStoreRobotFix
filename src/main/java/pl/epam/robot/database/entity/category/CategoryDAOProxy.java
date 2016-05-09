package pl.epam.robot.database.entity.category;

/**
 * Helper to manage Category entity
 * 
 * @author paulina
 *
 */
public interface CategoryDAOProxy {

	public void saveNewCategory(Category category);

	public Category findCategoryById(Integer id);

	public void deleteCategory(Category category);

	public Category findCategoryByCategoryType(String categoryType);
}

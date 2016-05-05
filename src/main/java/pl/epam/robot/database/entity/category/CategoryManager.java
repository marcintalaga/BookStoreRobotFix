package pl.epam.robot.database.entity.category;

public interface CategoryManager {

	public void saveNewCategory(Category category);

	public Category findCategoryById(Integer id);

	public void deleteCategory(Category category);

	public Category findCategoryByCategoryType(String categoryType);
}

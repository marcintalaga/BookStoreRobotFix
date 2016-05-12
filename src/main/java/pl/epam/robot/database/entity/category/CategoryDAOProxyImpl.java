package pl.epam.robot.database.entity.category;

import org.hibernate.HibernateException;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.category.CategoryDAO;
import pl.epam.robot.database.dao.category.CategoryDAOImpl;

public class CategoryDAOProxyImpl implements CategoryDAOProxy {

	CategoryDAO categoryDAO = new CategoryDAOImpl();

	@Override
	public void saveNewCategory(Category category) {
		try {
			HibernateSessionManager.beginTransaction();
			categoryDAO.save(category);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z zapisywaniem!" + ex.getMessage());
			HibernateSessionManager.rollbackTransaction();
		}
	}

	public Category findCategoryById(Integer id) {
		Category category = null;
		try {
			HibernateSessionManager.beginTransaction();
			category = (Category) categoryDAO.findByID(Category.class, id);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak przy szukaniu po id!");
		}
		return category;
	}

	@Override
	public void deleteCategory(Category category) {
		try {
			HibernateSessionManager.beginTransaction();
			categoryDAO.delete(category);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie z usuwaniem!");
		}
	}

	@Override
	public Category findCategoryByCategoryType(String categoryType) {
		Category category = null;
		try {
			HibernateSessionManager.beginTransaction();
			category = (Category) categoryDAO.findByCategoryType(categoryType);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak przy szukaniu po category type!");
		}
		return category;
	}
}

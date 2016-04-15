package pl.epam.robot.database.entity.category;

import org.hibernate.HibernateException;

import pl.epam.robot.database.dao.category.CategoryDAO;
import pl.epam.robot.database.dao.category.CategoryDAOImpl;
import pl.epam.utils.HibernateUtils;

public class CategoryManagerImpl implements CategoryManager {

	CategoryDAO categoryDAO = new CategoryDAOImpl();
	
	@Override
	public void saveNewCategory(Category category) {
		try {
            HibernateUtils.beginTransaction();
            categoryDAO.save(category);
            HibernateUtils.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z zapisywaniem!"+ ex.getMessage());
            HibernateUtils.rollbackTransaction();
        }		
	}
	
	 public Category findCategoryById(Integer id) {
	        Category category = null;
	        try {
	            HibernateUtils.beginTransaction();
	            category = (Category) categoryDAO.findByID(Category.class, id);
	            HibernateUtils.commitTransaction();
	        } catch (HibernateException ex) {
	            System.out.println("Cos poszlo nie tak przy szukaniu po id!");
	        }
	        return category;
	    }

}

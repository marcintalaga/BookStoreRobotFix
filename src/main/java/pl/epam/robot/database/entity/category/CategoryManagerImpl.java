package pl.epam.robot.database.entity.category;

import org.hibernate.HibernateException;

import pl.epam.robot.database.dao.CategoryDAO;
import pl.epam.robot.database.dao.CategoryDAOImpl;
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
            System.out.println("Cos poszlo nie tak z zapisywaniem!");
            HibernateUtils.rollbackTransaction();
        }		
	}

}

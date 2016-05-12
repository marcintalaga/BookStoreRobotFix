package pl.epam.robot.database.dao.category;

import org.hibernate.Query;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.GenericDAOImpl;
import pl.epam.robot.database.entity.category.Category;

public class CategoryDAOImpl extends GenericDAOImpl<Category, Integer> implements CategoryDAO {

	@Override
	public Category findByCategoryType(String categoryType) {
		Category category = null;
		String sql = "SELECT c FROM Category c WHERE c.categoryType = :categoryType";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("categoryType", categoryType);
		category = findOne(query);
		return category;
	}
}

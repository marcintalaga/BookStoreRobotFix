package pl.epam.robot.database.dao.tags;

import org.hibernate.Query;

import pl.epam.robot.database.HibernateUtils;
import pl.epam.robot.database.dao.GenericDAOImpl;
import pl.epam.robot.database.entity.tags.Tag;

public class TagDAOImpl extends GenericDAOImpl<Tag, Integer> implements TagDAO{

	@Override
	public Tag findById(int id) {
		Tag tag = null;
		String sql = "SELECT t FROM Tag t WHERE t.id = :id";
		Query query = HibernateUtils.getSession().createQuery(sql).setParameter("id", id);
		tag = findOne(query);
		return tag;
	}
	

}

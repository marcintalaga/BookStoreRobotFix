package pl.epam.robot.database.entity.tags;

import org.hibernate.HibernateException;

import pl.epam.robot.database.dao.tags.TagDAO;
import pl.epam.robot.database.dao.tags.TagDAOImpl;
import pl.epam.utils.HibernateUtils;

public class TagManagerImpl implements TagManager{

	TagDAO tagDAO = new TagDAOImpl();
	
	@Override
	public void saveNewTag(Tag tag) {
		try {
            HibernateUtils.beginTransaction();
            tagDAO.save(tag);
            HibernateUtils.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z zapisywaniem!"+ ex.getMessage());
            HibernateUtils.rollbackTransaction();
        }	
	}

}

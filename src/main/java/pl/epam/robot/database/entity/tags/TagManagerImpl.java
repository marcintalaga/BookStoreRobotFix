package pl.epam.robot.database.entity.tags;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import pl.epam.robot.database.HibernateUtils;
import pl.epam.robot.database.dao.tags.TagDAO;
import pl.epam.robot.database.dao.tags.TagDAOImpl;

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

	@Override
	public void deleteTag(Tag tag) {
		try {
            HibernateUtils.beginTransaction();
            tagDAO.delete(tag);
            HibernateUtils.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z usuwaniem!"+ ex.getMessage());
            HibernateUtils.rollbackTransaction();
        }		
	}

	@Override
	public Tag getTagById(int id) {
		Tag tag = null;
		try {
			HibernateUtils.beginTransaction();
			tag = tagDAO.findById(id);
			HibernateUtils.commitTransaction();
		} catch (NonUniqueResultException ex) {
			System.out.println("Query returned more than one results.");
		} catch (HibernateException ex) {
			System.out.println("Jakis inny problem z zapytaniem.");
		}
		return tag;
	}

}

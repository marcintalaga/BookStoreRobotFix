package pl.epam.robot.database.entity.tags;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.tags.TagDAO;
import pl.epam.robot.database.dao.tags.TagDAOImpl;

public class TagDAOProxyImpl implements TagDAOProxy{

	TagDAO tagDAO = new TagDAOImpl();
	
	@Override
	public void saveNewTag(Tag tag) {
		try {
            HibernateSessionManager.beginTransaction();
            tagDAO.save(tag);
            HibernateSessionManager.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z zapisywaniem!"+ ex.getMessage());
            HibernateSessionManager.rollbackTransaction();
        }	
	}

	@Override
	public void deleteTag(Tag tag) {
		try {
            HibernateSessionManager.beginTransaction();
            tagDAO.delete(tag);
            HibernateSessionManager.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z usuwaniem!"+ ex.getMessage());
            HibernateSessionManager.rollbackTransaction();
        }		
	}

	@Override
	public Tag getTagById(int id) {
		Tag tag = null;
		try {
			HibernateSessionManager.beginTransaction();
			tag = tagDAO.findById(id);
			HibernateSessionManager.commitTransaction();
		} catch (NonUniqueResultException ex) {
			System.out.println("Query returned more than one results.");
		} catch (HibernateException ex) {
			System.out.println("Jakis inny problem z zapytaniem.");
		}
		return tag;
	}

}

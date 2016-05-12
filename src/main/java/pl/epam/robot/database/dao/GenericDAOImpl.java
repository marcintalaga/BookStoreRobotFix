package pl.epam.robot.database.dao;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.epam.robot.database.HibernateSessionManager;

/**
 * Implementation of GenericDAO interface
 * 
 * @author paulina
 *
 * @param <T>
 *            element type
 * @param <ID>
 *            id type
 */
public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected Session getSession() {
		return HibernateSessionManager.getSessionFactory().getCurrentSession();
	}

	@Override
	public void save(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOne(Query query) {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByID(Class<?> clazz, Integer id) {
		Session hibernateSession = this.getSession();
		T t = null;
		t = (T) hibernateSession.get(clazz, id);
		return t;
	}
}

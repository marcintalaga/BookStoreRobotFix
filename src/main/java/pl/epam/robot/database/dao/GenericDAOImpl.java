package pl.epam.robot.database.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.epam.utils.HibernateUtils;

public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected Session getSession() {
		return HibernateUtils.getSessionFactory().getCurrentSession();
	}

	@Override
	public void save(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	@Override
	public void merge(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.merge(entity);
	}

	@Override
	public void delete(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findMany(Query query) {
		List<T> t;
		t = (List<T>) query.list();
		return t;
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
	public List<T> findAll(Class<?> clazz) {
		Session hibernateSession = this.getSession();
		List<T> T = null;
		Query query = hibernateSession.createQuery("from " + clazz.getName());
		T = query.list();
		return T;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByID(Class<?> clazz, BigDecimal id) {
		Session hibernateSession = this.getSession();
		T t = null;
		t = (T) hibernateSession.get(clazz, id);
		return t;
	}

}

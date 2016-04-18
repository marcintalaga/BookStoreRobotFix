package pl.epam.robot.database.dao;

import java.io.Serializable;

import org.hibernate.Query;


public interface GenericDAO<T, ID extends Serializable> {
		 
	    public void save(T entity);
	    public void delete(T entity);
//	    public List<T> findMany(Query query);
	    public T findOne(Query query);
//	    public List<?> findAll(Class<?> clazz);
	    public T findByID(Class<?> clazz, Integer id);
}

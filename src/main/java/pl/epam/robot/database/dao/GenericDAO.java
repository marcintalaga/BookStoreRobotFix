package pl.epam.robot.database.dao;

import java.io.Serializable;

import org.hibernate.Query;

/**
 * Base interface for database management
 * 
 * @author paulina
 *
 * @param <T>
 *            element type
 * @param <ID>
 *            id type
 */
public interface GenericDAO<T, ID extends Serializable> {

	public void save(T entity);

	public void delete(T entity);

	public T findOne(Query query);

	public T findByID(Class<?> clazz, Integer id);
}

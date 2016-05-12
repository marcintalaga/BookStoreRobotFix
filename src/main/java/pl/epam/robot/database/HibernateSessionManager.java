package pl.epam.robot.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Some methods that are helpful when you use Hibernate
 * 
 * @author paulina
 *
 */
public class HibernateSessionManager {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session beginTransaction() {
		Session hibernateSession = HibernateSessionManager.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction() {
		HibernateSessionManager.getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		HibernateSessionManager.getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		HibernateSessionManager.getSession().close();
	}

	public static Session getSession() {
		Session hibernateSession = sessionFactory.getCurrentSession();
		return hibernateSession;
	}
}

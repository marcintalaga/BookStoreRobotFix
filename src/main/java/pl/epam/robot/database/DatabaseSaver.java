package pl.epam.robot.database;

import org.hibernate.Session;

import pl.epam.robot.entity.Book;
import pl.epam.robot.entity.Bookstore;
import pl.epam.robot.entity.Category;
import pl.epam.utils.HibernateUtils;

public class DatabaseSaver {

	private Session session;

	public DatabaseSaver() {
		if (session == null) {
			session = HibernateUtils.getSessionFactory().openSession();
		}
	}

	public void saveBook(Book book) {
		session.beginTransaction();
		session.save(book);
		session.getTransaction().commit();
	}

	public void saveCategory(Category category) {
		session.beginTransaction();
		session.save(category);
		session.getTransaction().commit();
	}

	public void saveBookstore(Bookstore bookstore) {
		session.beginTransaction();
		session.save(bookstore);
		session.getTransaction().commit();
	}

	public void shutDown() {
		HibernateUtils.shutdown();
	}
}

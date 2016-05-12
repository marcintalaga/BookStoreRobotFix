package pl.epam.robot.database.entity.bookstore;

import org.hibernate.HibernateException;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.bookstore.BookstoreDAO;
import pl.epam.robot.database.dao.bookstore.BookstoreDAOImpl;

public class BookstoreDAOProxyImpl implements BookstoreDAOProxy {

	BookstoreDAO bookstoreDAO = new BookstoreDAOImpl();

	@Override
	public void saveNewBookstore(Bookstore bookstore) {
		try {
			HibernateSessionManager.beginTransaction();
			bookstoreDAO.save(bookstore);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z zapisywaniem!" + ex.getMessage());
			HibernateSessionManager.rollbackTransaction();
		}
	}

	@Override
	public void deleteBookstore(Bookstore bookstore) {
		try {
			HibernateSessionManager.beginTransaction();
			bookstoreDAO.delete(bookstore);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z usuwaniem!");
			HibernateSessionManager.rollbackTransaction();
		}
	}

}

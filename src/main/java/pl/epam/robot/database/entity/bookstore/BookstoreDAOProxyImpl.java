package pl.epam.robot.database.entity.bookstore;

import org.hibernate.HibernateException;

import pl.epam.robot.database.HibernateUtils;
import pl.epam.robot.database.dao.bookstore.BookstoreDAO;
import pl.epam.robot.database.dao.bookstore.BookstoreDAOImpl;

public class BookstoreDAOProxyImpl implements BookstoreDAOProxy {

	BookstoreDAO bookstoreDAO = new BookstoreDAOImpl();

	@Override
	public void saveNewBookstore(Bookstore bookstore) {
		try {
			HibernateUtils.beginTransaction();
			bookstoreDAO.save(bookstore);
			HibernateUtils.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z zapisywaniem!" + ex.getMessage());
			HibernateUtils.rollbackTransaction();
		}
	}

	@Override
	public void deleteBookstore(Bookstore bookstore) {
		try {
			HibernateUtils.beginTransaction();
			bookstoreDAO.delete(bookstore);
			HibernateUtils.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z usuwaniem!");
			HibernateUtils.rollbackTransaction();
		}
	}

}

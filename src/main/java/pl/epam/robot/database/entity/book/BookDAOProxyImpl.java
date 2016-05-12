package pl.epam.robot.database.entity.book;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.book.BookDAO;
import pl.epam.robot.database.dao.book.BookDAOImpl;

public class BookDAOProxyImpl implements BookDAOProxy {

	public BookDAO bookDAO = new BookDAOImpl();

	@Override
	public void saveNewBook(Book book) {
		try {
			HibernateSessionManager.beginTransaction();
			bookDAO.save(book);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z zapisywaniem ksiazki! " + ex.getMessage() + book.getTitleAndAuthor());
			HibernateSessionManager.rollbackTransaction();
		}
	}

	public Book findByBookTitleAndAuthor(String titleAndAuthor) {
		Book book = null;
		try {
			HibernateSessionManager.beginTransaction();
			book = bookDAO.findByTitleAndAuthor(titleAndAuthor);
			HibernateSessionManager.commitTransaction();
		} catch (NonUniqueResultException ex) {
			System.out.println("Query returned more than one results.");
		} catch (HibernateException ex) {
			System.out.println("Jakis inny problem z zapytaniem.");
		}
		return book;
	}

	@Override
	public void deleteBook(Book book) {
		try {
			HibernateSessionManager.beginTransaction();
			bookDAO.delete(book);
			HibernateSessionManager.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z usuwaniem!");
			HibernateSessionManager.rollbackTransaction();
		}
	}

}

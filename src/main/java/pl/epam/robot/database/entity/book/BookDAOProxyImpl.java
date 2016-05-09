package pl.epam.robot.database.entity.book;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import pl.epam.robot.database.HibernateUtils;
import pl.epam.robot.database.dao.book.BookDAO;
import pl.epam.robot.database.dao.book.BookDAOImpl;

public class BookDAOProxyImpl implements BookDAOProxy {

	BookDAO bookDAO = new BookDAOImpl();

	@Override
	public void saveNewBook(Book book) {
		try {
			HibernateUtils.beginTransaction();
			bookDAO.save(book);
			HibernateUtils.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z zapisywaniem ksiazki! " + ex.getMessage() + book.getTitleAndAuthor());
			HibernateUtils.rollbackTransaction();
		}
	}

	public Book findByBookTitleAndAuthor(String titleAndAuthor) {
		Book book = null;
		try {
			HibernateUtils.beginTransaction();
			book = bookDAO.findByTitleAndAuthor(titleAndAuthor);
			HibernateUtils.commitTransaction();
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
			HibernateUtils.beginTransaction();
			bookDAO.delete(book);
			HibernateUtils.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Cos poszlo nie tak z usuwaniem!");
			HibernateUtils.rollbackTransaction();
		}
	}

}

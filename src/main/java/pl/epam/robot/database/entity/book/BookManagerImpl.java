package pl.epam.robot.database.entity.book;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import pl.epam.robot.database.dao.book.BookDAO;
import pl.epam.robot.database.dao.book.BookDAOImpl;
import pl.epam.utils.HibernateUtils;

public class BookManagerImpl implements BookManager {

	BookDAO bookDAO = new BookDAOImpl();
	
	@Override
	public void saveNewBook(Book book) {
		try {
            HibernateUtils.beginTransaction();
            bookDAO.save(book);
            HibernateUtils.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z zapisywaniem!" + ex.getMessage());
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

}

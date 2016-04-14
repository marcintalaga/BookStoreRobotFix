package pl.epam.robot.database.entity.book;

import org.hibernate.HibernateException;

import pl.epam.robot.database.dao.BookDAO;
import pl.epam.robot.database.dao.BookDAOImpl;
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
            System.out.println("Cos poszlo nie tak z zapisywaniem!");
            HibernateUtils.rollbackTransaction();
        }		
	}

}

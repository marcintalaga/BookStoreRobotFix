package pl.epam.robot.database.entity.bookstore;

import org.hibernate.HibernateException;

import pl.epam.robot.database.dao.BookstoreDAO;
import pl.epam.robot.database.dao.BookstoreDAOImpl;
import pl.epam.utils.HibernateUtils;

public class BookstoreManagerImpl implements BookstoreManager {

	BookstoreDAO bookstoreDAO = new BookstoreDAOImpl();
	
	@Override
	public void saveNewBookstore(Bookstore bookstore) {
		try {
            HibernateUtils.beginTransaction();
            bookstoreDAO.save(bookstore);
            HibernateUtils.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Cos poszlo nie tak z zapisywaniem!");
            HibernateUtils.rollbackTransaction();
        }			
	}

}

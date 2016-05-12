package pl.epam.robot.database.dao.book;

import org.hibernate.Query;

import pl.epam.robot.database.HibernateSessionManager;
import pl.epam.robot.database.dao.GenericDAOImpl;
import pl.epam.robot.database.entity.book.Book;

public class BookDAOImpl extends GenericDAOImpl<Book, Integer> implements BookDAO {

	@Override
	public Book findByTitleAndAuthor(String titleAndAuthor) {
		Book book = null;
		String sql = "SELECT b FROM Book b WHERE b.titleAndAuthor = :titleAndAuthor";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("titleAndAuthor", titleAndAuthor);
		book = findOne(query);
		return book;
	}

}

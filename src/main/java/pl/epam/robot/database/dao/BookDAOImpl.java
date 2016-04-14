package pl.epam.robot.database.dao;

import org.hibernate.Query;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.utils.HibernateUtils;

public class BookDAOImpl extends GenericDAOImpl<Book, Integer> implements BookDAO {

	@Override
	public Book findByTitleAndAuthor(String titleAndAuthor) {
		Book book = null;
		String sql = "SELECT b FROM Book b WHERE b.titleAndAuthor = :titleAndAuthor";
		Query query = HibernateUtils.getSession().createQuery(sql).setParameter("titleAndAuthor", titleAndAuthor);
		book = findOne(query);
		return book;
	}

}

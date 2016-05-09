package pl.epam.robot.database.dao.book;

import pl.epam.robot.database.dao.GenericDAO;
import pl.epam.robot.database.entity.book.Book;

/**
 * Data Access Object for Book entity
 * 
 * @author paulina
 *
 */
public interface BookDAO extends GenericDAO<Book, Integer> {

	public Book findByTitleAndAuthor(String titleAndAuthor);

}

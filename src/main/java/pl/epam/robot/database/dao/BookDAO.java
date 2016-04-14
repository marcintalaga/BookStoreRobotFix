package pl.epam.robot.database.dao;

import pl.epam.robot.database.entity.book.Book;

public interface BookDAO extends GenericDAO<Book, Integer> {

	public Book findByTitleAndAuthor(String titleAndAuthor);
		
}

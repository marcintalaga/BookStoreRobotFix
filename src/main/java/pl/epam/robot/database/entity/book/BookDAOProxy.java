package pl.epam.robot.database.entity.book;

/**
 * Helper to manage Book entity
 * 
 * @author paulina
 *
 */
public interface BookDAOProxy {

	public void saveNewBook(Book book);

	public Book findByBookTitleAndAuthor(String titleAndAuthor);

	public void deleteBook(Book book);
}

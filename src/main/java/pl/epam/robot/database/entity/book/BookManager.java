package pl.epam.robot.database.entity.book;

public interface BookManager {

	public void saveNewBook(Book book);
	
	public Book findByBookTitleAndAuthor(String titleAndAuthor);
}
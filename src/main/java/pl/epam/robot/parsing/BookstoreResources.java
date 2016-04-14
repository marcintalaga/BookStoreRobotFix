package pl.epam.robot.parsing;

import java.util.Set;

import pl.epam.robot.database.entity.book.Book;

/**
 * 
 * Value object. Contains List of books from single bookstore + Sting name of
 * the bookstore.
 * 
 * @author Aleksander
 */
public class BookstoreResources {
	String bookstoreAddress;
	String bookStoreName;
	Set<Book> books;

	public BookstoreResources(String bookstoreAddress, String bookStoreName) {
		this.bookstoreAddress = bookstoreAddress;
		this.bookStoreName = bookStoreName;
	}

	public String getBookstore() {
		return bookstoreAddress;
	}
	
	public String getBookstoreName() {
		return bookStoreName;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}

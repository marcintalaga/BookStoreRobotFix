package pl.epam.robot.parsing;

import java.util.Set;

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
	Set<String> books;

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

	public Set<String> getBooks() {
		return books;
	}

	public void setBooks(Set<String> books) {
		this.books = books;
	}

}

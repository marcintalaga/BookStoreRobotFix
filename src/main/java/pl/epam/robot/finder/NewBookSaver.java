package pl.epam.robot.finder;

import java.util.Set;

import org.apache.log4j.Logger;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.book.BookDAOProxy;
import pl.epam.robot.database.entity.book.BookDAOProxyImpl;
import pl.epam.robot.database.entity.bookstore.Bookstore;
import pl.epam.robot.database.entity.bookstore.BookstoreDAOProxy;
import pl.epam.robot.database.entity.bookstore.BookstoreDAOProxyImpl;
import pl.epam.robot.parsing.category.FreeBookCategoryFinder;
import pl.epam.robot.parsing.tag.FreeBookTagsFinder;

public class NewBookSaver {

	private final static Logger bookslogger = Logger.getLogger("booksLogger");

	private Set<Book> freeBooks;
	private FreeBookCategoryFinder catfinder;
	private FreeBookTagsFinder tagger;
	private Bookstore bookstore;

	public NewBookSaver(Set<Book> freeBooks, String bookstoreName) {
		this.freeBooks = freeBooks;
		bookstore = new Bookstore();
		bookstore.setName(bookstoreName);
	}

	/**
	 * Saves entities (Categories,Tags,Bookstores) related to Book entity
	 */
	public void save() {
		BookstoreDAOProxy bm = new BookstoreDAOProxyImpl();
		bm.saveNewBookstore(bookstore);
		catfinder = new FreeBookCategoryFinder(bookstore.getName());
		tagger = new FreeBookTagsFinder(bookstore.getName());
		for (Book book : freeBooks) {
			saveSingleBook(book);
		}
	}

	/**
	 * Saves single Book entity
	 * 
	 * @param book
	 */
	private void saveSingleBook(Book book) {
		if (bookstore != null && bookstore.getName().equals("Publio")) {
			book.setTitleAndAuthor(book.getTitleAndAuthor().substring(book.getTitleAndAuthor().length() / 2));
		}
		book.setBookstore(bookstore);
		book.setTags(tagger.matchTags(book.getTitleAndAuthor()));
		book.setCategory(catfinder.matchCategories(book.getTitleAndAuthor()));
		BookDAOProxy bookmanager = new BookDAOProxyImpl();
		bookmanager.saveNewBook(book);
		bookslogger.info(book);
	}

}

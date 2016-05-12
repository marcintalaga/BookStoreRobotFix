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

	final static Logger bookslogger = Logger.getLogger("booksLogger");

	private Set<Book> freeBooks;
	private String bookStoreName;

	public NewBookSaver(Set<Book> freeBooks, String bookStoreName) {
		this.freeBooks = freeBooks;
		this.bookStoreName = bookStoreName;
	}

	public void save() {
		Bookstore bookstore = new Bookstore();
		bookstore.setName(bookStoreName);
		BookstoreDAOProxy bm = new BookstoreDAOProxyImpl();
		bm.saveNewBookstore(bookstore);
		FreeBookCategoryFinder catfinder = new FreeBookCategoryFinder(bookstore.getName());
		FreeBookTagsFinder tagger = new FreeBookTagsFinder(bookstore.getName());
		for (Book book : freeBooks) {
			if (bookStoreName.equals("Publio")) {
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

}

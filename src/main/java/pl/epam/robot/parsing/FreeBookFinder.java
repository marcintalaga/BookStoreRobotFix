package pl.epam.robot.parsing;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.book.BookManager;
import pl.epam.robot.database.entity.book.BookManagerImpl;
import pl.epam.robot.database.entity.bookstore.Bookstore;
import pl.epam.robot.database.entity.bookstore.BookstoreManager;
import pl.epam.robot.database.entity.bookstore.BookstoreManagerImpl;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryManager;
import pl.epam.robot.database.entity.category.CategoryManagerImpl;
import pl.epam.robot.parsing.FreeBookTagsFinder;

/**
 * @author Aleksander
 *
 */
public class FreeBookFinder {
	final static Logger bookslogger = Logger.getLogger("booksLogger");
	final static Logger logger = Logger.getLogger("logger");
	
	Set<Book> freeBooks = new HashSet<Book>();
	String pattern;
	String attr;

	/**
	 * Setting pattern and attribute as instance variables
	 * 
	 * @param pattern
	 * @param attr
	 */
	public FreeBookFinder(String pattern, String attr) {
		this.pattern = pattern;
		this.attr = attr;
	}

	/**
	 * Returns list of books from single bookstore. Logging an error if method
	 * can't connect to the URL from URL.properties.
	 * 
	 * @param urls
	 * @return List<String> freeBooks
	 */
	Set<Book> getFreeBooks(List<String> urls) {
		for (String url : urls) {
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				logger.error("Cant cant connect to " + url);
				break;
			}
			parseWebsite(doc);
		}
		return freeBooks;

	}

	/**
	 * Selecting website component based of pattern and attribute (instance
	 * variables) Adding selected component to the list. If there is no
	 * attribute in URL.properties method will add doc.select(pattern).text();
	 * 
	 * @see http://jsoup.org/cookbook/extracting-data/selector-syntax
	 * 
	 * @param Document-website
	 */
	private void parseWebsite(Document doc) {
		Elements url = doc.select(pattern);
		for (Element element : url) {
			if (attr.isEmpty()) {
				Book book = new Book();
				book.setTitleAndAuthor(element.text());
				freeBooks.add(book);
			} else {
				Book book = new Book();
				book.setTitleAndAuthor(element.attr(attr));
				freeBooks.add(book);
			}
		}
	}

	/**
	 * @param bookStoreName
	 * @param ds
	 */
	public void saveBooks(String bookStoreName) {
		
		CategoryManager cm = new CategoryManagerImpl();
		Category category = cm.findCategoryById(1);
		FreeBookTagsFinder tagger = new FreeBookTagsFinder();
		Bookstore bookstore = new Bookstore();
		bookstore.setName(bookStoreName);
		BookstoreManager bm = new BookstoreManagerImpl();
		bm.saveNewBookstore(bookstore);
		
		for (Book book : freeBooks) {
			book.setCategory(category);
			book.setBookstore(bookstore);
			book.setTags(tagger.getTags(book.getTitleAndAuthor()));
			System.out.println(book.toString());
			BookManager bookmanager = new BookManagerImpl();
			bookmanager.saveNewBook(book);
			
			bookslogger.info(book);
		}
	}
}

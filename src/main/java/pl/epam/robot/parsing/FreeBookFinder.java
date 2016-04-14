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
import pl.robot.enums.CategoryType;

/**
 * @author Aleksander
 *
 */
public class FreeBookFinder {
	final static Logger bookslogger = Logger.getLogger("booksLogger");
	final static Logger logger = Logger.getLogger("logger");
	Set<String> freeBooks = new HashSet<String>();
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
	Set<String> getFreeBooks(List<String> urls) {
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
				freeBooks.add(element.text());
			} else {
				freeBooks.add(element.attr(attr));
			}
		}
	}

	/**
	 * @param bookStoreName
	 * @param ds
	 */
	public void saveBooks(String bookStoreName) {
		
		Category category = new Category();
		category.setCategoryType(CategoryType.komedia);
		CategoryManager cm = new CategoryManagerImpl();
		cm.saveNewCategory(category);
		
		Bookstore bookstore = new Bookstore();
		bookstore.setName(bookStoreName);
		BookstoreManager bm = new BookstoreManagerImpl();
		bm.saveNewBookstore(bookstore);
		
		for (String string : freeBooks) {

			Book book = new Book();
			book.setTitleAndAuthor(string);
			book.setCategory(category);
			book.setBookstore(bookstore);
			BookManager bookmanager = new BookManagerImpl();
			bookmanager.saveNewBook(book);
			
			bookslogger.info(string);
		}
	}
}

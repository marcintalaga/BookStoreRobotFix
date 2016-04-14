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

import pl.epam.robot.database.DatabaseSaver;
import pl.epam.robot.entity.Book;
import pl.epam.robot.entity.Bookstore;
import pl.epam.robot.entity.Category;
import pl.epam.robot.entity.CategoryType;

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
	public void saveBooks(String bookStoreName, DatabaseSaver ds) {
		
		Category category = new Category();
		category.setCategoryType(CategoryType.komedia);
		ds.saveCategory(category);
		
		Bookstore bookstore = new Bookstore();
		bookstore.setName(bookStoreName);
		ds.saveBookstore(bookstore);
		
		for (String string : freeBooks) {

			Book book = new Book();
			book.setTitleAndAuthor(string);
			book.setCategory(category);
			book.setBookstore(bookstore);
			ds.saveBook(book);
			
			bookslogger.info(string);
		}
	}
}

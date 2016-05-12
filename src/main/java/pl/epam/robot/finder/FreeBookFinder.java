package pl.epam.robot.finder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pl.epam.robot.database.entity.book.Book;

/**
 * Class which is used to find free books Crucial class. It uses url.properties
 * to get connected to website, then it parses needed information from website
 * and saving it to database.
 * 
 * @author Aleksander
 *
 */
public class FreeBookFinder {
	final static Logger bookslogger = Logger.getLogger("booksLogger");
	final static Logger logger = Logger.getLogger("logger");

	private Set<Book> freeBooks = new HashSet<Book>();
	private String pattern;
	private String attr;

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
	 * can't connect to the URL from #URL.properties.
	 * 
	 * @param urls
	 * @return List<String> freeBooks
	 */
	public Set<Book> getFreeBooks(List<String> urls) {
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
	 * attribute in {@link /BookstoreRobot/src/main/resources/URL.properties}URL.properties method will add doc.select(pattern).text();
	 * 
	 * @see <a href="http://jsoup.org/cookbook/extracting-data/selector-syntax">
	 *      http://jsoup.org/cookbook/extracting-data/selector-syntax</a>
	 * 
	 * 
	 * @param Document
	 *            from website
	 */
	private void parseWebsite(Document doc) {
		Elements url = doc.select(pattern);
		url.forEach(e -> {
			if (attr.isEmpty() && !e.text().isEmpty()) {
				bookWithAuthor(e.text());
			} else if (!e.attr(attr).isEmpty()) {
				bookWithAuthor(e.attr(attr));
			}
		});
	}

	private void bookWithAuthor(String text) {
		Book book = new Book();
		book.setTitleAndAuthor(text);
		freeBooks.add(book);
	}
}

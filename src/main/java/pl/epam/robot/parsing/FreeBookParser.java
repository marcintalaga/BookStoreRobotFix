package pl.epam.robot.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.finder.FreeBookFinder;
import pl.epam.robot.finder.NewBookSaver;
import pl.epam.robot.urlGenerator.URL;
import pl.epam.robot.urlGenerator.URLGenerator;

/**
 * Here properties are loaded from external file. Free books list is filled in
 * with data.
 * 
 * @author Aleksander
 *
 */
public class FreeBookParser {
	final static Logger logger = Logger.getLogger("logger");
	final static Logger bookslogger = Logger.getLogger("booksLogger");

	private Properties properties;
	private List<URL> urls;
	private List<BookstoreResources> freeBooks;
	private FreeBookFinder finder;
	private NewBookSaver saver;

	public FreeBookParser() {
		properties = new Properties();
		urls = new URLGenerator().getUrls();
		freeBooks = new ArrayList<BookstoreResources>();
	}

	/**
	 * Return list of BookstoreResources (List of books + String bookstore)
	 * Logging list of free books in logs/books.log
	 * 
	 * @return List<BookstoreResources>
	 */
	public List<BookstoreResources> freeBooks() {
		loadProperties();
		int counter = 0;
		for (int i = 0; i < Integer.parseInt(properties.getProperty("NumberOfURLs")); i++) {
			if (!Boolean.valueOf(properties.getProperty(("active" + i)))) {
				continue;
			}
			String bookStoreName = properties.getProperty("name" + i);
			bookslogger.info(bookStoreName);

			freeBooks.add(new BookstoreResources(properties.get("URLPrefix" + i).toString(), bookStoreName));
			finder = new FreeBookFinder(properties.getProperty("pattern" + i), properties.getProperty("attr" + i));
			if (urls != null && !urls.isEmpty()) {
				Set<Book> freeBooksSet = finder.getFreeBooks(urls.get(i).getUrls());
				freeBooks.get(counter).setBooks(freeBooksSet);
				counter++;
				saver = new NewBookSaver(freeBooksSet, bookStoreName);
				saver.save();
			} else {
				logger.error("Pusta lista urli!");
			}
		}

		return freeBooks;
	}

	/**
	 * Loading properties file. Logging an error if can't find URL.properties in
	 * resources folder
	 */
	private void loadProperties() {
		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/URL.properties"),
				Charset.forName("UTF-8"));
		try {
			properties.load(fileReader);
		} catch (IOException e) {
			logger.error("Cant find 'URL.properties' file in resources folder");
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			logger.error("Cannot close stream" + e.getMessage());
		}
	}
}

package pl.epam.robot.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Aleksander
 *
 */
public class FreeBookParser {
	final static Logger logger = Logger.getLogger("logger");
	final static Logger bookslogger = Logger.getLogger("booksLogger");

	Properties properties = new Properties();
	List<URL> urls = new URLGenerator().getUrls();
	List<BookstoreResources> freeBooks = new ArrayList<BookstoreResources>();
	FreeBookFinder finder;

	public static void main(String[] args) {
		FreeBookParser f = new FreeBookParser();
		f.freeBooks();

	}

	/**
	 * Return list of BookstoreResources (List of books + String bookstore)
	 * Logging list of free books in logs/books.log
	 * 
	 * @return List<BookstoreResources>
	 */
	public List<BookstoreResources> freeBooks() {
		loadProperties();
		int counter =0;
		for (int i = 0; i < Integer.parseInt(properties.getProperty("NumberOfURLs")); i++) {
			if (!Boolean.valueOf(properties.getProperty(("active"+i)))) {
				continue;
			}
			System.out.println(properties.getProperty("name" + i));
			String bookStoreName = properties.getProperty("name" + i);
			bookslogger.info(bookStoreName);
			
			try {
			freeBooks.add(new BookstoreResources(properties.get("URLPrefix" + i).toString(), bookStoreName));
			finder = new FreeBookFinder(properties.getProperty("pattern" + i), properties.getProperty("attr" + i));
			freeBooks.get(counter).setBooks(finder.getFreeBooks(urls.get(i).getUrls()));
			counter++;
			} catch(NullPointerException e) {
				//logger.error("Null Pointer Exceptions");
			}
		}
		return freeBooks;
	}

	/**
	 * Loading properties file. Logging an error if can't find URL.properties in
	 * resources folder
	 */
	private void loadProperties() {
		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/URL.properties"));

		try {

			properties.load(fileReader);
		} catch (IOException e) {
			logger.error("Cant find 'URL.properties' file in resources folder");
		}
	}
}

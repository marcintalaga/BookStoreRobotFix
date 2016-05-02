package pl.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.parsing.FreeBookFinder;
import pl.epam.robot.parsing.FreeBookTagsFinder;
import pl.epam.robot.propertiesReader.PropertiesReader;
import pl.epam.robot.urlGenerator.URL;
import pl.epam.robot.urlGenerator.URLGenerator;

/**
 * A class which tests robots activity
 * @author paulina
 *
 */
public class RobotTest {
	
	/**
	 * Test getting tags from Nexto bookstore
	 */
	@Test
	public void testGettingTagsFromNexto() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder();
		String tags = fbtf.getTags("blabla", "Nexto");
		assertThat(tags).isNull();
	}
	
	/**
	 * Test getting tags from Publio bookstore
	 */
	@Test
	public void testGettingTagsFromPublio() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder();
		String tags = fbtf.getTags("blabla", "Publio");
		assertThat(tags).isNull();
	}
	
	/**
	 * Test getting books from default bookstore with no attribute/pattern set
	 */
	@Test 
	public void testGettingBooksFromNonExistingPartOfBookstore() {
		FreeBookFinder fbf = new FreeBookFinder("blabla", "blablabla");
		
		List<String> bookstores = new ArrayList<>();
		bookstores.add("http://www.helion.pl");
		Set<Book> books = fbf.getFreeBooks(bookstores);
		
		assertThat(books).isEmpty();
	}
	
	/**
	 * Test getting books - positive path, no atribute needed in this case
	 */
	@Test
	public void testGettingBooks() {
		FreeBookFinder fbf = new FreeBookFinder("div.bookListItem a", "blablabla");
		
		List<String> bookstores = new ArrayList<>();
		bookstores.add("http://www.legimi.com/pl/ebooki/darmowe/");
		Set<Book> books = fbf.getFreeBooks(bookstores);
		
		assertThat(books).isNotEmpty();
	}
	
//	@Test
//	public void testMockingBookstoreResources() {
//		BookstoreResources bs = mock(BookstoreResources.class);
//		Set<Book> books = new HashSet<>();
//		Book book = new Book();
//		books.add(book);
//
//		Mockito.when(bs.getBooks()).thenReturn(books);
//		
//		Mockito.verify(bs).equals(obj)
//		
//	}
	
	/**
	 * Test generating URLS by URLGenerator
	 */
	@Test
	public void testGeneratingURLs() {
		URLGenerator generator = new URLGenerator();
		
		List<URL> urls = generator.getUrls();
		
		assertThat(urls).isNotEmpty();
	}
	
	/**
	 * Test getting bookstores names from properties file
	 */
	@Test
	public void testGettingBookstoresNames() {
		PropertiesReader reader = new PropertiesReader();
		List<String> bookstoresNames = reader.getBookstoresNames();
		
		assertThat(bookstoresNames).isNotEmpty();
	}
	
//	@Test
//	public void testParsingFreeBooks() {
//		FreeBookParser fbp = new FreeBookParser();
//		
//		List<BookstoreResources> freeBooks = fbp.freeBooks();
//		
//		assertThat(freeBooks).isNotEmpty();
//	}
}

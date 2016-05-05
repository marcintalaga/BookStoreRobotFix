package pl.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.tags.Tag;
import pl.epam.robot.parsing.FreeBookFinder;
import pl.epam.robot.parsing.category.FreeBookCategoryFinder;
import pl.epam.robot.parsing.tag.FreeBookTagsFinder;
import pl.epam.robot.propertiesReader.PropertiesReader;
import pl.epam.robot.urlGenerator.URL;
import pl.epam.robot.urlGenerator.URLGenerator;

/**
 * A class which tests robots activity
 * 
 * @author paulina
 *
 */
public class RobotTest {

	/**
	 * Test getting tags from non existing bookstore
	 */
	@Test
	public void testGettingTags() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder("blablabl");
		Tag tag = fbtf.matchTags("blabla");
		assertThat(tag.getContent()).isEqualTo("default");
	}

	/**
	 * Test getting cats from non existing bookstore
	 */
	@Test
	public void testGettingCats() {
		FreeBookCategoryFinder fbcf = new FreeBookCategoryFinder("blablabl");
		Category cat = fbcf.matchCategories("blabla");
		assertThat(cat.getCategoryType()).isEqualTo("default");
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
		FreeBookFinder fbf = new FreeBookFinder("a.title", "");

		List<String> bookstores = new ArrayList<>();
		bookstores.add("http://www.nexto.pl/ebooki/darmowe_c1219.xml?_offset=");
		Set<Book> books = fbf.getFreeBooks(bookstores);

		assertThat(books).isNotEmpty();
	}

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

}

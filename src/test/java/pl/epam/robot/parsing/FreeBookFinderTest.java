package pl.epam.robot.parsing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;

public class FreeBookFinderTest {
	/**
	 * Test getting books from default bookstore with no attribute/pattern set
	 */
	@Test(groups = {"fastTests", "nonDatabaseTests"})
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
	@Test(groups = {"fastTests", "nonDatabaseTests"})
	public void testGettingBooks() {
		FreeBookFinder fbf = new FreeBookFinder("a.title", "");

		List<String> bookstores = new ArrayList<>();
		bookstores.add("http://www.nexto.pl/ebooki/darmowe_c1219.xml?_offset=");
		Set<Book> books = fbf.getFreeBooks(bookstores);

		assertThat(books).isNotEmpty();
	}
}

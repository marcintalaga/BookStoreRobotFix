package pl.epam.robot.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.book.BookDAOProxy;
import pl.epam.robot.database.entity.book.BookDAOProxyImpl;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryDAOProxy;
import pl.epam.robot.database.entity.category.CategoryDAOProxyImpl;

/**
 * Default database tests
 * 
 * @author paulina
 *
 */
public class DatabaseTest {

	@Test(groups = { "fastTests", "databaseTests" })
	public void testFindingBookByTitleAndAuthor() {
		Book book = new Book();
		book.setTitleAndAuthor("default title and author");
		BookDAOProxy bookManager = new BookDAOProxyImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isNotNull();

		Book book2 = bookManager.findByBookTitleAndAuthor("default title and author");

		assertThat(book2.getId()).isNotNull();
		assertThat(book.getId()).isEqualTo(book2.getId());

		bookManager.deleteBook(book);
	}

	@Test(groups = { "fastTests", "databaseTests" })
	public void testSavingBookWithoutTitle() {
		Book book = new Book();

		BookDAOProxy bookManager = new BookDAOProxyImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isEqualTo(0);
	}

	@Test(groups = { "fastTests", "databaseTests" })
	public void testFindingCategoryById() {
		Category cat = new Category();
		cat.setCategoryType("blabla");
		CategoryDAOProxy cm = new CategoryDAOProxyImpl();
		cm.saveNewCategory(cat);
		assertThat(cat.getId()).isNotNull();

		Category cat2 = cm.findCategoryById(cat.getId());

		assertThat(cat2.getId()).isNotNull();
		assertThat(cat2.getCategoryType()).isEqualTo(cat.getCategoryType());

		cm.deleteCategory(cat);
	}
}

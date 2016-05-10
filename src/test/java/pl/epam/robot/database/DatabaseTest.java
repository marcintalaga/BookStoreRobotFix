package pl.epam.robot.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.book.BookDAOProxy;
import pl.epam.robot.database.entity.book.BookDAOProxyImpl;
import pl.epam.robot.database.entity.bookstore.Bookstore;
import pl.epam.robot.database.entity.bookstore.BookstoreDAOProxy;
import pl.epam.robot.database.entity.bookstore.BookstoreDAOProxyImpl;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryDAOProxy;
import pl.epam.robot.database.entity.category.CategoryDAOProxyImpl;
import pl.epam.robot.database.entity.tags.Tag;
import pl.epam.robot.database.entity.tags.TagDAOProxy;
import pl.epam.robot.database.entity.tags.TagDAOProxyImpl;

/**
 * Default database tests
 * 
 * @author paulina
 *
 */
public class DatabaseTest {

	@Test(groups = "fastTests")
	public void testAddingAndDeletingBook() {
		Bookstore bookstore = new Bookstore();
		bookstore.setName("DefaultName");
		BookstoreDAOProxy bm = new BookstoreDAOProxyImpl();
		bm.saveNewBookstore(bookstore);
		assertThat(bookstore.getId()).isNotNull();

		Category cat = new Category();
		cat.setCategoryType("blabla");
		CategoryDAOProxy cm = new CategoryDAOProxyImpl();
		cm.saveNewCategory(cat);
		assertThat(cat.getId()).isNotNull();

		Tag tag = new Tag();
		tag.setContent("default content");
		TagDAOProxy tm = new TagDAOProxyImpl();
		tm.saveNewTag(tag);
		assertThat(tag.getId()).isNotNull();

		Book book = new Book();
		book.setBookstore(bookstore);
		book.setCategory(cat);
		book.setTags(tag);
		book.setTitleAndAuthor("default title and author");
		BookDAOProxy bookManager = new BookDAOProxyImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isNotNull();

		bookManager.deleteBook(book);
		tm.deleteTag(tag);
		bm.deleteBookstore(bookstore);
		cm.deleteCategory(cat);
	}

	@Test(groups = "fastTests")
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

	@Test(groups = "fastTests")
	public void testSavingBookWithoutTitle() {
		Book book = new Book();

		BookDAOProxy bookManager = new BookDAOProxyImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isEqualTo(0);
	}

	@Test(groups = "fastTests")
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

package pl.epam.robot.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.database.entity.book.BookManager;
import pl.epam.robot.database.entity.book.BookManagerImpl;
import pl.epam.robot.database.entity.bookstore.Bookstore;
import pl.epam.robot.database.entity.bookstore.BookstoreManager;
import pl.epam.robot.database.entity.bookstore.BookstoreManagerImpl;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryManager;
import pl.epam.robot.database.entity.category.CategoryManagerImpl;
import pl.epam.robot.database.entity.tags.Tag;
import pl.epam.robot.database.entity.tags.TagManager;
import pl.epam.robot.database.entity.tags.TagManagerImpl;
import pl.robot.enums.CategoryType;

public class DatabaseTests {

	@Test
	public void testAddingAndDeletingBook() {
		Bookstore bookstore = new Bookstore();
		bookstore.setName("DefaultName");
		BookstoreManager bm = new BookstoreManagerImpl();
		bm.saveNewBookstore(bookstore);
		assertThat(bookstore.getId()).isNotNull();

		Category cat = new Category();
		cat.setCategoryType(CategoryType.komedia);
		CategoryManager cm = new CategoryManagerImpl();
		cm.saveNewCategory(cat);
		assertThat(cat.getId()).isNotNull();

		Tag tag = new Tag();
		tag.setContent("default content");
		TagManager tm = new TagManagerImpl();
		tm.saveNewTag(tag);
		assertThat(tag.getId()).isNotNull();

		Book book = new Book();
		book.setBookstore(bookstore);
		book.setCategory(cat);
		book.setTags(tag);
		book.setTitleAndAuthor("default title and author");
		BookManager bookManager = new BookManagerImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isNotNull();

		bookManager.deleteBook(book);
		tm.deleteTag(tag);
		bm.deleteBookstore(bookstore);
		cm.deleteCategory(cat);
	}

	@Test
	public void testFindingBookByTitleAndAuthor() {
		Book book = new Book();
		book.setTitleAndAuthor("default title and author");
		BookManager bookManager = new BookManagerImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isNotNull();

		Book book2 = bookManager.findByBookTitleAndAuthor("default title and author");

		assertThat(book2.getId()).isNotNull();
		assertThat(book.getId()).isEqualTo(book2.getId());

		bookManager.deleteBook(book);
	}

	@Test
	public void testSavingBookWithoutTitle() {
		Book book = new Book();

		BookManager bookManager = new BookManagerImpl();
		bookManager.saveNewBook(book);
		assertThat(book.getId()).isEqualTo(0);
	}

	@Test
	public void testFindingCategoryById() {
		Category cat = new Category();
		cat.setCategoryType(CategoryType.komedia);
		CategoryManager cm = new CategoryManagerImpl();
		cm.saveNewCategory(cat);
		assertThat(cat.getId()).isNotNull();

		Category cat2 = cm.findCategoryById(cat.getId());

		assertThat(cat2.getId()).isNotNull();
		assertThat(cat2.getCategoryType()).isEqualTo(cat.getCategoryType());

		cm.deleteCategory(cat);
	}
}

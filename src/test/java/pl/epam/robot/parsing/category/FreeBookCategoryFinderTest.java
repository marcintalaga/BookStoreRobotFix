package pl.epam.robot.parsing.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.epam.robot.database.entity.category.Category;

public class FreeBookCategoryFinderTest {

	/**
	 * Test getting cats from non existing bookstore
	 */
	@Test(groups = {"fastTests", "nonDatabaseTests"})
	public void testGettingCats() {
		FreeBookCategoryFinder fbcf = new FreeBookCategoryFinder("blablabl");
		Category cat = fbcf.matchCategories("blabla");
		assertThat(cat.getCategoryType()).isEqualTo("default");
	}

	@DataProvider(name = "bookstoresForCats")
	public static Object[][] bookstoresForCats() {
		return new Object[][] { { "Legimi" }, { "Publio" }, { "Upoluj Ebooka" } };
	}

	/**
	 * Parametrized test of matching categories for non existing book title
	 * 
	 * @param bookstoreName
	 */
	@Test(dataProvider = "bookstoresForCats", groups = "nonDatabaseTests")
	public void testMatchingCategoriesForNonExistingBook(String bookstoreName) {
		FreeBookCategoryFinder fbcf = new FreeBookCategoryFinder(bookstoreName);

		Category c = fbcf.matchCategories("NonexistingBookTitle");

		assertThat(c.getCategoryType()).isEqualTo("default");
	}
}

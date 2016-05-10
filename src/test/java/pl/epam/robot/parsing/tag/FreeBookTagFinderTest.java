package pl.epam.robot.parsing.tag;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.epam.robot.database.entity.tags.Tag;

public class FreeBookTagFinderTest {
	
	/**
	 * Test getting tags from non existing bookstore
	 */
	@Test(groups = "fastTests")
	public void testGettingTags() {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder("blablabl");
		Tag tag = fbtf.matchTags("blabla");
		assertThat(tag.getContent()).isEqualTo("default");
	}

	@DataProvider(name = "bookstoresForTags")
	public static Object[][] bookstoresForTags() {
		return new Object[][] { { "Nexto" }, { "Publio" } };
	}

	/**
	 * Parametrized test of matching tags for non existing book title
	 * 
	 * @param bookstoreName
	 */
	@Test(dataProvider = "bookstoresForTags", groups = "fastTests")
	public void testMatchingTagsForNonExistingBook(String bookstoreName) {
		FreeBookTagsFinder fbtf = new FreeBookTagsFinder(bookstoreName);
		Tag t = fbtf.matchTags("NonexistingBookTitle");

		assertThat(t.getContent()).isEqualTo("default");
	}
}

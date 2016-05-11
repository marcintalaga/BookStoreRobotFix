package pl.epam.robot.parsing;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;


public class FreeBookParserTest {
	/**
	 * Here we check if method is not returning empty list.
	 */
	@Test
	public void testIfBooksAreProperlyAdded() {
		List<BookstoreResources> list;
		FreeBookParser fbp = new FreeBookParser();
		list = fbp.freeBooks();
		assertThat(list.isEmpty()).isFalse();
	}
}
package pl.epam.robot.parsing.category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Acquires categories from Legimi site
 * 
 * @author paulina
 *
 */
public class LegimiCategoryFinder implements CategoryFinder {

	@Override
	public Map<String, String> getCategories() {

		Map<String, String> legimiCat = new HashMap<String, String>();
		String absHref;
		Elements elements;
		Elements titles;
		Elements spanElements;
		String spanText;
		String bookTitle;
		Document particularBookConnection;
		try {
			Document document = Jsoup.connect("http://www.legimi.com/pl/ebooki/darmowe/").get();
			Elements ebooks = document.select("div.bookListItem a[href]");

			for (int i = 0; i < ebooks.size(); i++) {

				absHref = ebooks.get(i).attr("abs:href");
				particularBookConnection = Jsoup.connect(absHref).get();
				elements = particularBookConnection.select("div.moreInfo");
				titles = particularBookConnection.select("div.leftColTop h1");

				bookTitle = titles.text();
				if (bookTitle.length() > 15) {
					bookTitle = bookTitle.substring(0, (bookTitle.length() - 15));

				}
				try {
					if (elements.size() > 0) {
						spanElements = elements.get(0).getElementsByTag("span");
						for (int j = 0; j < spanElements.size(); j++) {
							spanText = spanElements.get(j).text();
							if (spanText.contains("Kategoria")) {
								legimiCat.put(bookTitle, spanText.substring(11));
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return legimiCat;
	}

}

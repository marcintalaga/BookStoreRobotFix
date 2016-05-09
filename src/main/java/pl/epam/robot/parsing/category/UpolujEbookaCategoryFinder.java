package pl.epam.robot.parsing.category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Acquires categories from Upoluj Ebooka site
 * 
 * @author paulina
 *
 */
public class UpolujEbookaCategoryFinder implements CategoryFinder {

	@Override
	public Map<String, String> getCategories() {
		Map<String, String> upolujCat = new HashMap<String, String>();
		String absHref;
		Elements category;
		String title;
		Elements ebooks;
		try {
			Document doc = Jsoup.connect("http://upolujebooka.pl/kategoria,8248,darmowe_e-booki.html").get();
			ebooks = doc.select("div.name a");
			for (int i = 0; i < ebooks.size(); i++) {
				title = ebooks.select("h2").get(i).text();
				absHref = ebooks.get(i).attr("abs:href");
				category = Jsoup.connect(absHref).get().select("div.category div.fleft a[title]");
				upolujCat.put(title, category.text());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return upolujCat;
	}

}

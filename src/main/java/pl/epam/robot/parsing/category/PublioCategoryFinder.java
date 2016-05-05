package pl.epam.robot.parsing.category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Downloads categories from Publio site
 * 
 * @author paulina
 *
 */
public class PublioCategoryFinder implements CategoryFinder {

	@Override
	public Map<String, String> getCategories() {
		Map<String, String> publioCat = new HashMap<String, String>();
		String absHref;
		Elements elements;
		String title;
		Elements categories;
		Document particularBookConnection;
		try {
			Document doc = Jsoup.connect("http://www.publio.pl/e-booki,darmowe.html").get();
			Elements ebooks = doc.select("h3.product-tile-title a");

			for (int i = 0; i < ebooks.size(); i++) {
				absHref = ebooks.get(i).attr("abs:href");
				particularBookConnection = Jsoup.connect(absHref).get();
				elements = particularBookConnection.select("div.product-detail");

				title = particularBookConnection.select("h1.title ").text();
				for (Element element2 : elements) {
					if ("Publikacja z kategorii:".equals(element2.select("div.product-detail-label").text())) {

						categories = element2.select("div.product-detail-value");
						publioCat.put(title, categories.text());
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return publioCat;
	}

}

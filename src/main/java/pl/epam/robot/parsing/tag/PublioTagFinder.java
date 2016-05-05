package pl.epam.robot.parsing.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Get tags from Publio website
 * 
 * @author paulina
 *
 */
public class PublioTagFinder implements TagFinder {

	@Override
	public Map<String, String> getTags() {
		Map<String, String> publioTags = new HashMap<String, String>();
		String absHref;
		Elements elements;
		String bookTitle;
		Elements productDetailLabel;
		Document particularBookConnection;

		try {
			Document doc = Jsoup.connect("http://www.publio.pl/e-booki,darmowe.html").get();

			Elements ebooks = doc.select("h3.product-tile-title a");

			for (int i = 0; i < ebooks.size(); i++) {
				absHref = ebooks.get(i).attr("abs:href");
				particularBookConnection = Jsoup.connect(absHref).get();
				elements = particularBookConnection.select("div.product-detail");
				bookTitle = particularBookConnection.select("h1.title ").text();
				for (Element element2 : elements) {
					if ("Tematy i słowa kluczowe:".equals(element2.select("div.product-detail-label").text())) {

						productDetailLabel = element2.select("div.product-detail-value");
						publioTags.put(bookTitle, productDetailLabel.text());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publioTags;
	}

}

package pl.epam.robot.parsing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.epam.robot.database.entity.tags.Tag;
import pl.epam.robot.database.entity.tags.TagManager;
import pl.epam.robot.database.entity.tags.TagManagerImpl;

public class FreeBookTagsFinder {

	private String bookstoreName;
	private Map<String, String> tags;
	private Tag tag;

	public FreeBookTagsFinder(String bookstoreName) {
		this.bookstoreName = bookstoreName;
		initializeTags();
	}

	private void initializeTags() {
		tags = new HashMap<String, String>();
		if (bookstoreName.equals("Nexto")) {
			tags = getNextoTags();
		} else if (bookstoreName.equals("Publio")) {
			tags = getPublioTags();
		}
	}

	public Tag matchTags(String bookTitle) {
		TagManager tagManager = new TagManagerImpl();
		if (tags != null && !tags.isEmpty()) {
			Set<Entry<String, String>> set = tags.entrySet();
			for (Entry<String, String> entry : set) {
				if (entry.getKey().equals(bookTitle)) {
					tag = new Tag();
					tag.setContent(entry.getValue());
					tagManager.saveNewTag(tag);
					return tag;
				}
			}
		}
		return tag;
	}

	private Map<String, String> getPublioTags() {
		Map<String, String> publioTags = new HashMap<String, String>();
		try {
			Document doc = Jsoup.connect("http://www.publio.pl/e-booki,darmowe.html").get();

			Elements ebooks = doc.select("h3.product-tile-title a");

			for (int i = 0; i < ebooks.size(); i++) {
				String absHref = ebooks.get(i).attr("abs:href");
				Elements elements = Jsoup.connect(absHref).get().select("div.product-detail");

				String title = Jsoup.connect(absHref).get().select("h1.title ").text();
				for (Element element2 : elements) {
					if ("Tematy i słowa kluczowe:".equals(element2.select("div.product-detail-label").text())) {

						Elements elements2 = element2.select("div.product-detail-value");
						publioTags.put(title, elements2.text());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publioTags;
	}

	private HashMap<String, String> getNextoTags() {
		HashMap<String, String> nextoTags = new HashMap<String, String>();
		try {
			Document doc = Jsoup.connect("http://www.nexto.pl/ebooki/darmowe_c1219.xml?_offset=").get();

			Elements ebooks = doc.select("a.title");

			for (int i = 0; i < ebooks.size(); i++) {
				String absHref = ebooks.get(i).attr("abs:href");

				Elements url = Jsoup.connect(absHref).get().select("div.panel2 div.tags a");
				String title = Jsoup.connect(absHref).get().select("div.panel2 h1").text();
				StringBuffer buffer = new StringBuffer();

				for (int j = 0; j < 5; j++) {
					buffer.append(url.get(j).text());
					buffer.append(", ");
				}
				nextoTags.put(title.toString(), buffer.toString());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nextoTags;
	}

}

package pl.epam.robot.parsing.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Get tags from Nexto website
 * 
 * @author paulina
 *
 */
public class NextoTagFinder implements TagFinder {

	@Override
	public Map<String, String> getTags() {
		HashMap<String, String> nextoTags = new HashMap<String, String>();
		String absHref;
		Elements tags;
		String bookTitle;
		StringBuffer buffer;
		Document particularBookConnection;

		try {
			Document doc = Jsoup.connect("http://www.nexto.pl/ebooki/darmowe_c1219.xml?_offset=").get();
			Elements ebooks = doc.select("a.title");

			for (int i = 0; i < ebooks.size(); i++) {
				absHref = ebooks.get(i).attr("abs:href");
				particularBookConnection = Jsoup.connect(absHref).get();
				tags = particularBookConnection.select("div.panel2 div.tags a");
				bookTitle = particularBookConnection.select("div.panel2 h1").text();
				buffer = new StringBuffer();
				for (int j = 0; j < 5; j++) {
					buffer.append(tags.get(j).text());
					buffer.append(", ");
				}
				nextoTags.put(bookTitle.toString(), buffer.toString());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nextoTags;
	}

}

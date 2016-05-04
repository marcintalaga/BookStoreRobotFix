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

import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryManager;
import pl.epam.robot.database.entity.category.CategoryManagerImpl;

public class FreeBookCategoriesFinder {

	private String bookTitle;
	private String bookstoreName;
	private Category cat;

	public FreeBookCategoriesFinder(String bookTitle, String bookstoreName) {
		this.bookTitle = bookTitle;
		this.bookstoreName = bookstoreName;
	}

	public Category matchCategories() {
		
		CategoryManager catManager = new CategoryManagerImpl();
		Map<String, String> categories = new HashMap<String, String>();

		if (bookstoreName.equals("Legimi")) {
			categories = getLegimiCategories();
		} else if (bookstoreName.equals("Publio")) {
			categories = getPublioCategories();
		} else if (bookstoreName.equals("Upoluj Ebooka")) {
			categories = getUpolujEbookaCategories();
		}

		if (categories != null && !categories.isEmpty()) {
			Set<Entry<String, String>> set = categories.entrySet();
			for (Entry<String, String> entry : set) {
				if (entry.getKey().equals(bookTitle)) {
					cat = new Category();
					cat.setCategoryType(entry.getValue());
					catManager.saveNewCategory(cat);
					return cat;
				}
			}
		}
		return cat;
	}
	

	private Map<String, String> getLegimiCategories() {

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

	private Map<String, String> getPublioCategories() {
		Map<String, String> publioCat = new HashMap<String, String>();
		
		try {
			Document doc = Jsoup.connect("http://www.publio.pl/e-booki,darmowe.html").get();

			Elements ebooks = doc.select("h3.product-tile-title a");
			String absHref;
			Elements elements;
			String title;
			Elements elements2;
			Document particularBookConnection;
			for (int i = 0; i < ebooks.size(); i++) {
				absHref = ebooks.get(i).attr("abs:href");
				particularBookConnection = Jsoup.connect(absHref).get();
				elements = particularBookConnection.select("div.product-detail");
				
				 title = particularBookConnection.select("h1.title ").text();
				for (Element element2 : elements) {
					if ("Publikacja z kategorii:".equals(element2.select("div.product-detail-label").text())) {
						
						elements2 = element2.select("div.product-detail-value");
						publioCat.put(title, elements2.text());
					}
				}
			}
		

		} catch (IOException e) {
			e.printStackTrace();
		}
		return publioCat;
	}

	private Map<String, String> getUpolujEbookaCategories() {
		Map<String, String> upolujCat = new HashMap<String, String>();
		String absHref;
		Elements category;
		String title;
		Elements ebooks;
		try{
			Document doc = Jsoup.connect("http://upolujebooka.pl/kategoria,8248,darmowe_e-booki.html").get();
			ebooks = doc.select("div.name a");
			for (int i = 0; i < ebooks.size(); i++) {
				title = ebooks.select("h2").get(i).text();
				absHref = ebooks.get(i).attr("abs:href");
				category = Jsoup.connect(absHref).get().select("div.category div.fleft a[title]");
				upolujCat.put(title, category.text());
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return upolujCat;
	}

}

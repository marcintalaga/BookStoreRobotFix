package pl.epam.robot.parsing.category;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.category.CategoryManager;
import pl.epam.robot.database.entity.category.CategoryManagerImpl;

/**
 * An object which is used to match book with its category - finds the category
 * and saves it to DB
 * 
 * @author paulina
 *
 */
public class FreeBookCategoryFinder {

	private String bookstoreName;
	private Map<String, String> categories;
	private Category cat;
	private CategoryFinder catFinder;

	public FreeBookCategoryFinder(String bookstoreName) {
		this.bookstoreName = bookstoreName;
		initializeCategories();
	}

	private void initializeCategories() {
		categories = new HashMap<String, String>();
		if (bookstoreName.equals("Legimi")) {
			catFinder = new LegimiCategoryFinder();
		} else if (bookstoreName.equals("Publio")) {
			catFinder = new PublioCategoryFinder();
		} else if (bookstoreName.equals("Upoluj Ebooka")) {
			catFinder = new UpolujEbookaCategoryFinder();
		}
		if (catFinder != null) {
			categories = catFinder.getCategories();
		}
	}

	public Category matchCategories(String bookTitle) {
		CategoryManager catManager = new CategoryManagerImpl();
		if (categories != null && !categories.isEmpty()) {
			Set<Entry<String, String>> set = categories.entrySet();
			for (Entry<String, String> entry : set) {
				if (entry.getKey().toString().startsWith(bookTitle)
						|| bookTitle.startsWith(entry.getKey().toString())) {
					cat = catManager.findCategoryByCategoryType(entry.getValue());
					if (cat != null) {
						return cat;
					} else {
						cat = new Category();
						cat.setCategoryType(entry.getValue());
						catManager.saveNewCategory(cat);
						return cat;
					}
				} else {
					cat = catManager.findCategoryById(1);
				}
			}
		} else {
			cat = catManager.findCategoryById(1);
		}
		return cat;
	}
}

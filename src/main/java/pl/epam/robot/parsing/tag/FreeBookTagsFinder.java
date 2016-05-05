package pl.epam.robot.parsing.tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.epam.robot.database.entity.tags.Tag;
import pl.epam.robot.database.entity.tags.TagManager;
import pl.epam.robot.database.entity.tags.TagManagerImpl;

/**
 * Match tags with books
 * 
 * @author paulina
 *
 */
public class FreeBookTagsFinder {

	private String bookstoreName;
	private Map<String, String> tags;
	private Tag tag;
	private TagFinder tagFinder;

	public FreeBookTagsFinder(String bookstoreName) {
		this.bookstoreName = bookstoreName;
		initializeTags();
	}

	/**
	 * Initializes map with tags and titles of books depending on bookstore
	 */
	private void initializeTags() {
		tags = new HashMap<String, String>();
		if (bookstoreName.equals("Nexto")) {
			tagFinder = new NextoTagFinder();
		} else if (bookstoreName.equals("Publio")) {
			tagFinder = new PublioTagFinder();
		}
		if (tagFinder != null) {
			tags = tagFinder.getTags();
		}
	}

	/**
	 * Matches tags with concrete book
	 * 
	 * @param bookTitle
	 * @return tags
	 */
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
		} else {
			tag = tagManager.getTagById(1);
		}
		return tag;
	}

}

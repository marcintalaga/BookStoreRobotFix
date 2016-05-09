package pl.epam.robot.database.entity.book;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.epam.robot.database.entity.bookstore.Bookstore;
import pl.epam.robot.database.entity.category.Category;
import pl.epam.robot.database.entity.tags.Tag;

/**
 * Book entity
 * 
 * @author paulina
 *
 */
@Entity
@Table(name = "BOOKS")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID", unique = true, nullable = false)
	private int id;

	@Column(name = "TITLE_AND_AUTHOR", nullable = false)
	private String titleAndAuthor;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "BOOKSTORE_ID")
	private Bookstore bookstore;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "TAG_ID")
	private Tag tags;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Bookstore getBookstore() {
		return bookstore;
	}

	public void setBookstore(Bookstore bookstore) {
		this.bookstore = bookstore;
	}

	public String getTitleAndAuthor() {
		return titleAndAuthor;
	}

	public void setTitleAndAuthor(String titleAndAuthor) {
		this.titleAndAuthor = titleAndAuthor;
	}

	public Tag getTags() {
		return tags;
	}

	public void setTags(Tag tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", tytuł i autor=" + titleAndAuthor + ", kategoria=" + category + ", księgarnia="
				+ bookstore + ", tagi=" + tags + "]";
	}
}

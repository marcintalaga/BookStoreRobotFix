package pl.epam.robot.database.entity.book;

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

@Entity
@Table(name = "BOOKS")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID", unique = true, nullable = false)
	private int id;
	
	@Column(name="TITLE_AND_AUTHOR")
	private String titleAndAuthor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOKSTORE_ID", nullable = false)
	private Bookstore bookstore;
	
	@Column(name="TAGS")
	private String tags;


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
	
	public String getTags() {
		return tags;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
	}
}

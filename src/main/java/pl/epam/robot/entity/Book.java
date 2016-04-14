package pl.epam.robot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID", unique = true, nullable = false)
	private int id;
	
	@Column(name="TITLE_AND_AUTHOR")
	private String titleAndAuthor;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "AUTHOR_ID", nullable = false)
//	private Author author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOKSTORE_ID", nullable = false)
	private Bookstore bookstore;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


//	public Author getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(Author author) {
//		this.author = author;
//	}

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
	
	
}

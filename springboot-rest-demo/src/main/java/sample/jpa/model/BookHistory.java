package sample.jpa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity  //테이블과 매핑할 클래스임을 지정
@Table(name="BOOKHISTORY") //Entity가 연결될 테이블 명 지정
public class BookHistory {
	
	
	/*
	 * <sample>

	{
	"name": "bookName1",
	"isbn": "b111",
	"author": "joo1",
	"pages": 101
	}

	*/
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="book_id", unique = true, nullable = false)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String isbn;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private int pages;
	
	@Column(name="history_id",nullable = true)
	private String historyId;
	
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch=FetchType.EAGER)
    @JoinColumn(name="info_id", nullable = true, unique = true, insertable = true, updatable = false)
	private BookInfo bookInfo;
	

	public BookHistory() {
	}

	public BookHistory(String name, String isbn, String author, int pages, String historyId, BookInfo bookInfo) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.pages = pages;
		this.historyId = historyId;
		this.bookInfo = bookInfo;
	}
	
	public BookHistory(long id, String name, String isbn, String author, int pages, String historyId, BookInfo bookInfo) {
		this.id = id;
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.pages = pages;
		this.historyId = historyId;
		this.bookInfo = bookInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	
	
}
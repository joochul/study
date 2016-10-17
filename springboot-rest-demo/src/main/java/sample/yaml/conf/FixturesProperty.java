package sample.yaml.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import sample.jpa.model.BookHistory;
import sample.mongo.model.Book;


//@ConfigurationProperties(locations="classpath:servers.yml")

@Component
@ConfigurationProperties(prefix = "bookstore")
public class FixturesProperty {
    private List<Book> books = new ArrayList<>();
    
    private List<BookHistory> bookHistorys = new ArrayList<>();

	public List<Book> getBooks() {
		System.out.println("[SIZE_BOOKS]"+books.size());
		return books;
	}

	public List<BookHistory> getBookHistorys() {
		return bookHistorys;
	}
	
}
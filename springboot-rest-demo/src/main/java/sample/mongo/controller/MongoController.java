package sample.mongo.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.mongo.model.Book;
import sample.mongo.repository.BookRepository;

@RestController
@RequestMapping("/mongo")
public class MongoController {

	@Autowired
	private BookRepository bookRepository;
		
	
	/******************************************************
	 * Spring-data-mongodb
	 ******************************************************/
	
	/**
	 * save
	 * 
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createBookMongo(
			@RequestBody Map<String, Object> bookMap) {
		Book book = new Book(bookMap.get("name").toString(), bookMap
				.get("isbn").toString(), bookMap.get("author").toString(),
				Integer.parseInt(bookMap.get("pages").toString()));

		bookRepository.save(book);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Book created successfully");
		response.put("book", book);
		return response;
	}
	
	/**
	 * get List
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllBooksMongo(){
	    List<Book> books = bookRepository.findAll();
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("totalBooks", books.size());
	    response.put("books", books);
	    return response;
	  }

	/**
	 * search
	 * 
	 * @param bookId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
	public Book getBookDetailsMongo(@PathVariable("bookId") String bookId) {
		return bookRepository.findOne(bookId);
	}

	/**
	 * update
	 * 
	 * @param bookId
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{bookId}")
	public Map<String, Object> editBookMongo(@PathVariable("bookId") String bookId,
			@RequestBody Map<String, Object> bookMap) {
		
		Book book = new Book(bookMap.get("name").toString(), bookMap
				.get("isbn").toString(), bookMap.get("author").toString(),
				Integer.parseInt(bookMap.get("pages").toString()));
		book.setId(bookId);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Book Updated successfully");
		response.put("book", bookRepository.save(book));
		return response;
	}

	/**
	 * delete
	 * 
	 * @param bookId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
	public Map<String, String> deleteBookMongo(@PathVariable("bookId") String bookId) {
		
		bookRepository.delete(bookId);
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "Book deleted successfully");

		return response;
	}
	
	
}
package sample.jpa.controller;

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

import sample.jpa.model.BookHistory;
import sample.jpa.model.BookInfo;
import sample.jpa.repository.BookHistoryRepository;

@RestController
@RequestMapping("/jpa")
public class JpaController {
	
	@Autowired
	private BookHistoryRepository bookHistoryRepository;
	 
	
	
	/**********************************************
	 * Spring-data-jpa (MYSQL)
	 **********************************************/
	/**
	 * save
	 * 
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createBookMySql(
			@RequestBody Map<String, Object> bookMap) {
		
		BookInfo info = new BookInfo(bookMap.get("author").toString(), "2016.07.21");
		
		BookHistory bookH = new BookHistory(
					  bookMap.get("name").toString()
					, bookMap.get("isbn").toString()
					, bookMap.get("author").toString()
					, Integer.parseInt(bookMap.get("pages").toString())
					, null
					, info
					);

		bookHistoryRepository.save(bookH);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "book_history created successfully");
		response.put("book_history", bookH);
		return response;
	}
	
	/**
	 * get List
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllBooksMysql(){
	    List<BookHistory> bookHs = bookHistoryRepository.findAll();
	    
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("totalBooks", bookHs.size());
	    response.put("book_history", bookHs);
	    return response;
	  }

	/**
	 * search
	 * 
	 * @param bookId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
	public Map<String, Object> getBookDetailsMysql(@PathVariable("bookId") long bookId) {
		
		BookHistory bookH = bookHistoryRepository.findOne(bookId);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "book_history created successfully");
		response.put("book_history", bookH);
		return response;
	}

	/**
	 * update
	 * 
	 * @param bookId
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{bookId}")
	public Map<String, Object> editBookMysql(@PathVariable("bookId") long bookId,
			@RequestBody Map<String, Object> bookMap) {
		
		
		BookHistory bookH = bookHistoryRepository.findOne(bookId);
		
		bookH.setName(bookMap.get("name").toString());
		bookH.setIsbn(bookMap.get("isbn").toString());
		bookH.setAuthor(bookMap.get("author").toString());
		bookH.setPages(Integer.parseInt(bookMap.get("pages").toString()));
		
		bookHistoryRepository.save(bookH);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "book_history Updated successfully");
		response.put("book_history", bookH);
		return response;
	}

	/**
	 * delete
	 * 
	 * @param bookId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
	public Map<String, String> deleteBookMysql(@PathVariable("bookId") long bookId) {
		
		bookHistoryRepository.delete(bookId);
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "book_history deleted successfully");

		return response;
	}
	
	
}
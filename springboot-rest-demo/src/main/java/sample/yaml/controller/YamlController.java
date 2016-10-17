package sample.yaml.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.jpa.model.BookHistory;
import sample.jpa.model.BookInfo;
import sample.jpa.repository.BookHistoryRepository;
import sample.mongo.model.Book;
import sample.mongo.repository.BookRepository;
import sample.yaml.conf.FixturesProperty;

@RestController
@RequestMapping("/yaml")
public class YamlController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookHistoryRepository bookHistoryRepository;

	@Autowired
    private FixturesProperty fixturesProperty;
	
	@Autowired
    ConfigurableApplicationContext context;
	
	
	/****************************************
	 * TES YAML
	 ****************************************/
	
	/**
	 * TEST YAML
	 * 
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> testBook() {
		
		List<Book> books = fixturesProperty.getBooks();
    	int result = books.size();
    	System.out.println("[SIZE]"+result);
    	
    	for(Book book : books){
    		System.out.println("---------------------------");
    		System.out.println("[id]"+book.getId());
    		System.out.println("[isbn]"+book.getIsbn());
    		System.out.println("[name]"+book.getName());
    		System.out.println("[author]"+book.getAuthor());
    		System.out.println("[pages]"+book.getPages());
    	}
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Book config successfully");
		response.put("books", books);
		
		
		
		
		MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("get ymal!");
            }
        };
        
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println("Sending a new message.");
        jmsTemplate.send("mailbox-destination", messageCreator);
		
		return response;
	}
	
	/**
	 * TEST INSERT YAML
	 * 
	 * insert mongoDB & mysql
	 * 
	 * & 연속성 테스트
	 * 
	 * @param bookMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/ins")
	public Map<String, Object> testInsBook() {
		
		List<Book> books = fixturesProperty.getBooks();
    	int result = books.size();
    	System.out.println("[SIZE]"+result);
    	
    	
    	List<BookHistory> bookHistorys = fixturesProperty.getBookHistorys();
    	
    	boolean historyIsNull = false;
    	if(bookHistorys.size()==0){
    		historyIsNull = true;
    	}
    	
    	int i=0;
    	for(Book book : books){
    		System.out.println("---------------------------");
    		System.out.println("[id]"+book.getId());
    		System.out.println("[isbn]"+book.getIsbn());
    		System.out.println("[name]"+book.getName());
    		String name = book.getName();
    		book.setName(name+"_R");
    		
    		System.out.println("[author]"+book.getAuthor());
    		System.out.println("[pages]"+book.getPages());
    		
    		bookRepository.save(book);
    		System.out.println("<INSERT_MONGO> ... OK");
    		
    		
    		
    		BookHistory bookH = null;
    		
    		if(historyIsNull){
    			
    			BookInfo info = new BookInfo("seoul_"+i,"2016.07.21");
    			
    			bookH = new BookHistory(book.getName(),book.getIsbn(),book.getAuthor(),book.getPages(),book.getId(),info);
    			bookHistorys.add(bookH);
    		}else{
    			bookH = bookHistorys.get(i);
    			bookH.setName(name+"_R");
    		}
    		
    		bookHistoryRepository.save(bookH);
    		System.out.println("<INSERT_MYSQL> ... OK");
    		
    		i++;
    	}
    	
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Books created successfully");
		response.put("books", books);
		return response;
	}
	
}
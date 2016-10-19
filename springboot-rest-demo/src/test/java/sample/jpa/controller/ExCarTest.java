package sample.jpa.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.Application;
import sample.ServletInitializer;
import sample.jpa.model.BookHistory;
import sample.jpa.repository.BookHistoryRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@ContextConfiguration(classes = ServletInitializer.class)
public class ExCarTest {
	
	@Autowired
	private BookHistoryRepository bookHistoryRepository;
	
	@Test
	public void 테스트_BookHistoryRepository() {
		
		List<BookHistory> bookHs = bookHistoryRepository.findAll();
		
		System.out.println("[SIZE]"+bookHs.size());
		
		assertEquals(bookHs.size(), 5);
	}
	

}

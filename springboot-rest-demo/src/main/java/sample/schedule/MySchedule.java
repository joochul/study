package sample.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.jpa.model.BookHistory;
import sample.jpa.repository.BookHistoryRepository;
import sample.mongo.model.Book;
import sample.mongo.repository.BookRepository;

@Component
public class MySchedule {
	
	private static int cnt = 0;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookHistoryRepository bookHistoryRepository;
	
    //@Scheduled(fixedDelay=1000*5)
    //@Transactional
    public void updateUrbanServiceArea() {

    	cnt ++;
    	
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	List<Book> books = bookRepository.findAll();
    	
    	System.out.println("ACT_CNT:"+cnt+"==== Spring Task :"+dateFormat.format(calendar.getTime()));
    	System.out.println("[MongoDB]");
    	for(Book book : books){
    		System.out.println("<TASK>[id]"+book.getId());
    		System.out.println("<TASK>[isbn]"+book.getIsbn());
    		System.out.println("<TASK>[name]"+book.getName());
    		System.out.println("<TASK>[author]"+book.getAuthor());
    		System.out.println("<TASK>[pages]"+book.getPages());
    		
    		if(!book.getName().startsWith("bookName")){
    			System.out.println("Delete >>> "+book.getName());
    			bookRepository.delete(book);
    			System.out.println("Delete ... ok");
    		}
    	}
    	
    	System.out.println("[MySql]");
    	List<BookHistory> bookHistorys = bookHistoryRepository.findAll();
    	for(BookHistory bookHistory : bookHistorys){
    		System.out.println("<TASK>[id]"+bookHistory.getId());
    		System.out.println("<TASK>[isbn]"+bookHistory.getIsbn());
    		System.out.println("<TASK>[name]"+bookHistory.getName());
    		System.out.println("<TASK>[author]"+bookHistory.getAuthor());
    		System.out.println("<TASK>[pages]"+bookHistory.getPages());
    		
    		if(!bookHistory.getName().startsWith("bookName")){
    			System.out.println("Delete >>> "+bookHistory.getName());
    			bookHistoryRepository.delete(bookHistory);
    			System.out.println("Delete ... ok");
    		}
    	}
    	System.out.println("\n");
    }
    
}	





/*

<bean id=”jobDetailBean” Class=”o.s.scheduling.quartz.JobDetailBean”>
             <property name = “jobClass” value=” {package}.SayHelloJob “/>
             <property name = “jobDataAsMap”
                        <map>
                                  <entry key = “name” value=”JobDetail”/>
                        </map>
             </property>
</bean>



## SimpleTriggerBean 을 이용한 설정
<bean id=”simpleTrigger” class = “o.s.scheduling.quartz.SimpleTriggerBean”>
           <property name=”jobDetail” ref=”jobDetailBean”/>
           <property name=”startDelay” value=”0”/>   //즉시시작
           <property name=”repeatInterval” value=”10000”/> // 매 10초마다 실행
</bean>
 
## CronTriggerBean 을 이용한 설정
<bean id=”cronTrigger” class = “o.s.scheduling.quartz.CronTriggerBean”>
           <property name=”jobDetail” ref=”jobDetailFactoryBean”/>
           <property name=”cronExpression” value=”*\/10 * * * * ? “ />
</bean>
 
 
 
 
## 작업 시작하기
<bean id=”scheduler” class=”o.s.scheduling.quartz.SchedulerFactoryBean”>
           <property name = “triggers”>
                     <list>
                                <ref bean=”simpleTrigger”/>
                                <ref bean=”cronTrigger”/>
                     </list>
           </property>
</bean>

 
 */

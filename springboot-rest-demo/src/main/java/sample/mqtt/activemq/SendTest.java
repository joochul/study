package sample.mqtt.activemq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendTest {
	
	private static int cnt = 0;
	
	public static ArrayList<Destination> queueList = null;
    
    /**
     * ActiveMQ - TYPE 1
     */
    @Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
    
    /**
     * call schedule
     * 
     * @throws JMSException
     * @throws IOException 
     */
    @Scheduled(fixedDelay=1000*5)
    public void testActiveMQ() throws JMSException, IOException {
    	long nowTime = System.currentTimeMillis();
    	Date date = Calendar.getInstance().getTime();
    	
    	cnt ++;
    	sendTest(cnt, "["+cnt+"]current millis => "+nowTime+", date=>"+date.getDate());
		System.out.println("Message was sent to the Queue");
    }
    
    
    public void sendTest(int cnt, String msg) throws JMSException, IOException {
    	
    	if(queueList == null){
    		queueList = new ArrayList<Destination>();
    		
    		Queue queueMap    = new ActiveMQQueue("activemq-map");
        	Queue queueStream = new ActiveMQQueue("activemq-stream");
        	Queue queueFile   = new ActiveMQQueue("activemq-stream-file");
        	Queue queueByte   = new ActiveMQQueue("activemq-byte");
        	Queue queueText   = new ActiveMQQueue("activemq-text");
        	
        	Topic topic01  = new ActiveMQTopic("/topic/users");
        	
        	queueList.add(queueMap);
        	queueList.add(queueStream);
        	queueList.add(queueFile);
        	queueList.add(queueByte);
        	queueList.add(queueText);
        	queueList.add(topic01);
        	
    	}
    	
    	System.out.println("\n\n===================================");

    	for(Destination destination: queueList){
    		
    		if(destination instanceof Queue){
    			
    			String queueName = ((Queue)destination).getQueueName();
    			
    			//MAP
        		if(queueName.indexOf("map") > 0){
        			sendMap(destination, queueName, cnt, msg);
        			
        			
        		//STREAM
        		}else if(queueName.indexOf("stream") > 0){
        			sendStream(destination, queueName);
        			

        		//BYTE	
        		}else if(queueName.indexOf("byte") > 0){
        			sendByte(destination, queueName, msg);
        			
        			
        		//TEXT
        		}else if(queueName.indexOf("text") > 0){
        			this.jmsMessagingTemplate.convertAndSend(destination, msg);

        			
        		}else{
        			
        		}
        		
        		
    		}else if(destination instanceof Topic){
    			this.jmsMessagingTemplate.convertAndSend(destination, msg);
    		}
    		
    		
    		
    	}//end of for
		
		System.out.println("===================================\n\n");
	}
	
    
    
    
    /**
     * 
     * @param queue
     * @param queueName
     */
    public void sendMap(Destination queue, String queueName,int cnt, String msg){
    	Properties prop = new Properties();
    	prop.setProperty("queueName", queueName);
    	prop.setProperty("cnt", ""+cnt);
    	prop.setProperty("msg", msg);
    	
		this.jmsMessagingTemplate.convertAndSend(queue, prop);
    }
    
    /**
     * 
     * @param queue
     * @param queueName
     * @throws IOException
     * @throws JMSException
     */
    public void sendStream(Destination queue, String queueName) throws IOException, JMSException{
    	//5번에 한번만 구동
		if(queueName.indexOf("file") > 0 && cnt%5!=0 ){
			return;
		}
		
		ActiveMQConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		
		
		FileInputStream is = null;
		File file = new File("D:/JOO/sample.csv");
		// int size = 1024 * 100;
		int sendByteSize = 100;
		
		try{
			factory = (ActiveMQConnectionFactory) this.jmsMessagingTemplate.getConnectionFactory();
			connection = factory.createConnection("admin", "secret");
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer producer = session.createProducer(queue);
			// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			StreamMessage streamMessage = session.createStreamMessage();
			streamMessage.setJMSReplyTo(queue);

			
			if(queueName.indexOf("file") > 0){
    			is = new FileInputStream(file);

    			
    			byte[] bytes = new byte[sendByteSize];

    			for (int count; (count = is.read(bytes)) > 0;) {
    				streamMessage.clearBody();
    				streamMessage.writeBytes(bytes, 0, count);
    				producer.send(streamMessage);

    				System.out.println("CNT:" + count);
    			}
    			
    			// send endflag
    			byte[] endByte = "SMEND".getBytes();
    			streamMessage.clearBody();
    			streamMessage.writeBytes(endByte);
    			producer.send(streamMessage);
			}
			else{
				
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}finally{
			
			if(is != null){
				is.close();
				is = null;
			}
			
			session.close();
			connection.close();
		}
    }
    
    /**
     * 
     * @param queue
     * @param queueName
     */
    public void sendByte(Destination queue, String queueName, String msg) throws IOException, JMSException{
    	ActiveMQConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		
		try{
			factory = (ActiveMQConnectionFactory) this.jmsMessagingTemplate.getConnectionFactory();
			connection = factory.createConnection("admin", "secret");
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer producer = session.createProducer(queue);
			
			BytesMessage bytesMessage = session.createBytesMessage();
			bytesMessage.setJMSReplyTo(queue);
			
			bytesMessage.writeBoolean(false);
			bytesMessage.writeDouble(123.456789e22);
			bytesMessage.writeInt(778899);
			bytesMessage.writeInt(0x7f800000);
			bytesMessage.writeChar('z');
			bytesMessage.writeObject(msg);
			
			bytesMessage.reset();
			
			producer.send(queue, bytesMessage);
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}finally{
			session.close();
			connection.close();
		}
    }
    
    
}	

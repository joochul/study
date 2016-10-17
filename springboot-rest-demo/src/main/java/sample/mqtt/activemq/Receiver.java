package sample.mqtt.activemq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	
	//public static FileOutputStream fos = null;
	public static ByteArrayOutputStream outputStream = null;
	
	
	
	@JmsListener(destination = "activemq-topic01", subscription="activemq-topic")
    public void receiveTopic01(Message message) {
        System.out.println("receiveTopic01 <" + message + ">");
    }
	
	@JmsListener(destination = "activemq-topic02", subscription="activemq-topic")
    public void receiveTopic02(Message message) {
        System.out.println("receiveTopic02 <" + message + ">");
    }
	
	@JmsListener(destination = "activemq-topic03", subscription="activemq-topic")
    public void receiveTopic03(Message message) {
        System.out.println("receiveTopic03 <" + message + ">");
    }
	
	
	/*
	@JmsListener(destination = "activemq-text", containerFactory = "myJmsContainerFactory")
    public void receiveMessageTxt(Message message) {
        System.out.println("activemq-text <" + message + ">");
    }
	
	@JmsListener(destination = "activemq-map", containerFactory = "myJmsContainerFactory")
    public void receiveMessageMap(Message message) {
		try {
        	String name  = message.getJMSDestination().toString();//queue name을 알 수 있다.
        	
        	if (message instanceof MapMessage) {
            	System.out.println("<<MapMessage>>\n[NAME]"+name);
            	MapMessage mapMessage = (MapMessage) message;
            	
            	System.out.println("[MAP]---------------------------");
            	Enumeration<String> mapNames =  mapMessage.getMapNames();
            	while(mapNames.hasMoreElements()){
            		String value = mapNames.nextElement();
            		String key   = mapMessage.getString(value);
            		System.out.println("\tvalue:"+value+", key:"+key);
            	}
            	
            	System.out.println("[PROPERTY]-----------------------");
            	Enumeration<String> propertyNames =  mapMessage.getPropertyNames();
            	while(propertyNames.hasMoreElements()){
            		String value = propertyNames.nextElement();
            		String key   = mapMessage.getStringProperty(value);
            		System.out.println("\tvalue:"+value+", key:"+key);
            	}
            	System.out.println("---------------------------------");
            }
            
            System.out.println("[Destination] '" + message.toString() + "'\n");            
            
        } catch (JMSException e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    }
	*/
	
	/**
	 * 
	 * @param message
	 * @throws IOException 
	 */
	@JmsListener(destination = "activemq-stream-file", containerFactory = "myJmsContainerFactory")
//	@JmsListener(destination = "*", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) throws IOException {
		
		try {
        	String name  = message.getJMSDestination().toString();//queue name을 알 수 있다.
        	
        	// TEXT
            if (message instanceof TextMessage) {
            	System.out.println("<<TextMessage>>\n[NAME]"+name);
                TextMessage textMessage = (TextMessage) message;
                System.out.println("[MESSAGE]" + textMessage.getText());
                
            //MAP
            }else if (message instanceof MapMessage) {
            	System.out.println("<<MapMessage>>\n[NAME]"+name);
            	MapMessage mapMessage = (MapMessage) message;
            	
            	System.out.println("[MAP]---------------------------");
            	Enumeration<String> mapNames =  mapMessage.getMapNames();
            	while(mapNames.hasMoreElements()){
            		String value = mapNames.nextElement();
            		String key   = mapMessage.getString(value);
            		System.out.println("\tvalue:"+value+", key:"+key);
            	}
            	
            	System.out.println("[PROPERTY]-----------------------");
            	Enumeration<String> propertyNames =  mapMessage.getPropertyNames();
            	while(propertyNames.hasMoreElements()){
            		String value = propertyNames.nextElement();
            		String key   = mapMessage.getStringProperty(value);
            		System.out.println("\tvalue:"+value+", key:"+key);
            	}
            	System.out.println("---------------------------------");
            	
            	
            //STREAM
            }else if (message instanceof StreamMessage) {
            	System.out.println("<<StreamMessage>>\n[NAME]"+name);
            	System.out.println("[CONTENT]-----------------------");
            	StreamMessage streamMessage = (StreamMessage) message;
            	
            	if(name.indexOf("file")>0){
	                if(outputStream == null){
	                	System.out.println("<<CREATE ByteArrayOutputStream>>");
	                	//fos = new FileOutputStream("D:/JOO/samplecopy.csv", true);
	                	outputStream = new ByteArrayOutputStream();
	                }
	                
	                //int sendByteSize = 1024 * 100;
	                int sendByteSize = 100;
	                byte[] endByte = "SMEND".getBytes();
	                
	                
	                byte[] bytes = new byte[sendByteSize];
	                for (int count; (count = streamMessage.readBytes(bytes)) > 0; ) {
	                	
	                	//result
	                	String str = new String(bytes,0,count);
	                	
	                	if(str.equals("SMEND")){
	                		System.out.println("END_FLAG:"+str);
	                		System.out.println(outputStream.toString());
	                		
	                		outputStream.close();
	                		outputStream = null;
	                	}else{
	                		
	                		outputStream.write(bytes, 0, count);
		                	System.out.println("CNT:"+count);
	                	}
	                }
            	}else{
            		
            	}
            	
            	System.out.println("---------------------------------");
            
            	
        	//BYTE
            }else if (message instanceof BytesMessage) {
            	System.out.println("<<BytesMessage>>\n[NAME]"+name);
            	BytesMessage bytesMessage = (BytesMessage) message;
            	
            	long length = bytesMessage.getBodyLength();
            	
            	System.out.println("[CONTENT]-----------------------");
            	System.out.println("Reading BytesMessages of various types:(length:"+length+")");
        		System.out.println(" Boolean: " + bytesMessage.readBoolean());
        		System.out.println(" Double: " + bytesMessage.readDouble());
        		System.out.println(" Int: " + bytesMessage.readInt());
        		System.out.println(" Float: " + bytesMessage.readFloat());
        		System.out.println(" Char: " + bytesMessage.readChar());
        		System.out.println(" object: " + bytesMessage.readUTF());
        		System.out.println("---------------------------------");
            }
            
            
            System.out.println("[Destination] '" + message.toString() + "'\n");            
            
        } catch (JMSException e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    	
    }
	
}
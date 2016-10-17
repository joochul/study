package sample.mqtt.activemq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReceiverOther {
	
	/**
	 * 
	 * @param message
	 */
//	@JmsListener(destination = "activemq-other", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("Received_other <" + message + ">");
    }
	
}
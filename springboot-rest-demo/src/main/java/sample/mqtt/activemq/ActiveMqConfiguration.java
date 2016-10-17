package sample.mqtt.activemq;


import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

@Configuration
public class ActiveMqConfiguration {

	@Bean // Strictly speaking this bean is not necessary as boot creates a default
	JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}
	
}


/*
		<!--JMS support-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jms</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-jms</artifactId>
        </dependency>
        <!--ActiveMQ-->
		<dependency>
		  <groupId>org.apache.activemq</groupId>
		  <artifactId>activemq-all</artifactId>
		  <version>5.14.0</version>
		</dependency>


###############
## ACTIVE MQ ##
## https://github.com/spring-projects/spring-boot/blob/v1.4.0.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/activemq/ActiveMQProperties.java
###############
spring.activemq.in-memory=true
spring.activemq.pool.enabled=false

## http://activemq.apache.org/vm-transport-reference.html
## 외부 broker를 사용
#spring.activemq.broker-url=tcp://127.0.0.1:61616

## embedded broker를 외부에 오픈할 수 있음
spring.activemq.broker-url=vm:(broker:(tcp://localhost:6000)?persistent=false)?marshal=false

#spring.activemq.user=admin
#spring.activemq.password=secret

*/
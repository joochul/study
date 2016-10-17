package sample.webSocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import sample.webSocket.model.Greeting;
import sample.webSocket.model.HelloMessage;

@Controller
public class GreetingController {

	
    @MessageMapping("/topichello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
    	System.out.println("input hello");
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
}

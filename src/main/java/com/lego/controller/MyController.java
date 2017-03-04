package com.lego.controller;

import com.lego.model.Greeting;
import com.lego.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MyController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting");
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @RequestMapping("/test")
    public String test(HttpSession session){
        System.out.println("test");
        webSocket.convertAndSend("/topic/greetings", new Greeting("test"));
        return "test";
    }
}

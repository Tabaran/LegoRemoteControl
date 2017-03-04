package com.lego.controller;

import com.lego.model.Greeting;
import com.lego.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        webSocket.convertAndSend("/topic/greetings", new Greeting("test"));
        return "test";
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("helloMessage", new HelloMessage());
        return "index";
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String send(@ModelAttribute HelloMessage message){
        webSocket.convertAndSend("/topic/greetings", new Greeting(message.getName()));
        return "index";
    }
}

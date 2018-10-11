package com.zwxq.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sent")
public class SentController {
    @RequestMapping("/sentMessage")
    public String sentMessage(){

        return "sentMessage";
    }

    @RequestMapping("/pastMessage")
    public String pastMessage(){

        return "pastMessage";
    }
}

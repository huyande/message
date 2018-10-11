package com.zwxq.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/uploadFile")
    public String pastMessage(MultipartFile file){

        return "true";
    }


}

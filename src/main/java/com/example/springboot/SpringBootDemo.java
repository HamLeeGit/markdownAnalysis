package com.example.springboot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootDemo {

    @RequestMapping("/hello")
    public String  Demo(){
       return "My First SpringBoot";
    }



}

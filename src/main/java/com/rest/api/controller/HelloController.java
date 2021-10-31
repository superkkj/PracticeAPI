package com.rest.api.controller;


import com.rest.api.entity.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello/string")
    public String helloworldString(){
        return "hello!";
    }

    @GetMapping("hello/json")
    public Hello helloworldJson(){
        Hello hello = new Hello();
        hello.message ="hello";
        return hello;
    }
}

package com.temp.inflow.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeomoController {

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @GetMapping("/test/{message}")
    public String testWithParam(@PathVariable String message){
        return "pathVarible: " + message;
    }
}

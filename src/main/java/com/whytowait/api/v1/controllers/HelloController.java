package com.whytowait.api.v1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, spring";
    }

    @GetMapping("/")
    public String test() {
        return "server up and running on 8080";
    }
}

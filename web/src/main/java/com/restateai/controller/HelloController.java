package com.restateai.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello - " + authentication.getName();
    }
}

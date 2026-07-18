package com.inventra.inventra_backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(Authentication authentication) {

        return "Welcome " + authentication.getName()
                + ", JWT Authentication is working!";
    }
}
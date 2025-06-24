package com.example.productstore.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/api/secure/data")
    public String secureData() {
        return "This is protected data!";
    }

    @GetMapping("/api/public/data")
    public String publicData() {
        return "This is public data!";
    }
}



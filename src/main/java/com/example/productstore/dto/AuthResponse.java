package com.example.productstore.dto;

public class AuthResponse {

    public AuthResponse() {
    }

    private String token;


    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}



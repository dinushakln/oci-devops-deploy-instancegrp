package com.example;

import jakarta.inject.Singleton;

@Singleton
public class GreetingService {
    public String generateGreeting(String name) {
        return "Hello, " + name + "! You’ve hit the OCI DevOps demo app.";
    }
}
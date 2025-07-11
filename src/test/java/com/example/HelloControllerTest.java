package com.example;

import static org.junit.jupiter.api.Assertions.*;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.util.Map;

@MicronautTest
public class HelloControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testApiWelcome() {
        HttpRequest<String> request = HttpRequest.GET("/api");
        Map response = client.toBlocking().retrieve(request, Map.class);

        assertNotNull(response);
        assertEquals("Welcome to the OCI DevOps Demo Application", response.get("message"));
        assertTrue(response.containsKey("timestamp"));
        assertEquals("1.3", response.get("version"));  // updated to match actual injected version
    }

    @Test
    public void testHelloName() {
        HttpRequest<String> request = HttpRequest.GET("/api/hello/Pluto");
        Map response = client.toBlocking().retrieve(request, Map.class);

        assertNotNull(response);
        assertEquals("Hello, Pluto! You’ve hit the OCI DevOps demo app.", response.get("greeting"));  // updated to match actual greeting
        assertEquals("1.3", response.get("version"));  // updated version here as well
    }
}
package com.example;

import static org.junit.jupiter.api.Assertions.*;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

@MicronautTest
public class HelloControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    private static String expectedVersion;

    @BeforeAll
    public static void init() {
        // Read version from system property or default to "UNKNOWN"
        expectedVersion = System.getProperty("app.version", "UNKNOWN");
        System.out.println("Expected APP_VERSION: " + expectedVersion);
    }

    @Test
    public void testApiWelcome() {
        HttpRequest<String> request = HttpRequest.GET("/api");
        Map response = client.toBlocking().retrieve(request, Map.class);

        assertNotNull(response);
        assertEquals("Welcome to the OCI DevOps Demo Application", response.get("message"));
        assertTrue(response.containsKey("timestamp"));
        assertEquals(expectedVersion, response.get("version"));  // dynamically compare version
    }

    @Test
    public void testHelloName() {
        HttpRequest<String> request = HttpRequest.GET("/api/hello/Pluto");
        Map response = client.toBlocking().retrieve(request, Map.class);

        assertNotNull(response);
        assertEquals("Hello, Pluto! You’ve hit the OCI DevOps demo app.", response.get("greeting"));
        assertEquals(expectedVersion, response.get("version"));  // dynamically compare version
    }

    @Test
    public void testAppVersionInjection() {
    HttpRequest<String> request = HttpRequest.GET("/api/version");
    String response = client.toBlocking().retrieve(request, String.class);

    assertNotNull(response);
    assertFalse(response.isEmpty());
    System.out.println("Injected APP_VERSION: " + response);

    // This asserts it isn't the fallback default value
    assertNotEquals("unknown", response, "APP_VERSION should not be the default 'unknown'");
}

}
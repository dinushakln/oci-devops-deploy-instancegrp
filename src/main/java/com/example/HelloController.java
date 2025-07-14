package com.example;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller("/api")
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Value("${app.version}")
    protected String appVersion;

    @Inject
    protected GreetingService greetingService;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> welcome() {
        LOG.info("Accessed /api endpoint");
        return HttpResponse.ok(Map.of(
                "message", "Welcome to the OCI DevOps Demo Application",
                "timestamp", LocalDateTime.now(),
                "version", appVersion
        ));
    }

    @Get("/hello/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> greetUser(@PathVariable String name) {
        LOG.info("Greeting request for user: {}", name);
        return HttpResponse.ok(Map.of(
                "greeting", greetingService.generateGreeting(name),
                "timestamp", LocalDateTime.now(),
                "version", appVersion
        ));
    }

    @Get("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> health() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.warn("Unable to determine hostname", e);
            hostname = "unknown";
        }

        return HttpResponse.ok(Map.of(
                "status", "UP",
                "checkedAt", LocalDateTime.now(),
                "hostname", hostname,
                "version", appVersion
        ));
    }

    @Get("/version")
    @Produces(MediaType.TEXT_PLAIN)
    public String version() {
        return appVersion;
    }
}
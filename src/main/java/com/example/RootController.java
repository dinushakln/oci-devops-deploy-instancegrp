package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class RootController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String root() {
        return "Welcome to the OCI DevOps Java App! Visit /api for full application details.";
    }
}
package com.app.musiclover.api.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthResource {

    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/hello-secured")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecured() {
        return "Hello World Secured";
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('CREATE')")
    public String hello2() {
        return "Hello World";
    }
}

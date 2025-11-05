package com.app.musiclover.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi3Config {
    @Bean(name = "musicLoverOpenApi")
    public OpenAPI musicLoverOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Music Lover API")
                        .description("Music Lover implemented with Spring Boot RESTful service " +
                                "and documented using springdoc-openapi-starter-webmvc-ui and OpenAPI 3.0"));
    }
}
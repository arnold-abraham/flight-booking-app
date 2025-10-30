package com.example.flightbooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final String schemeName = "bearer-jwt";

        return new OpenAPI()
                .info(new Info()
                        .title("Flight Booking API")
                        .description("MVP Flight booking service with JWT security")
                        .version("v1"))
                .components(new Components().addSecuritySchemes(
                        schemeName,
                        new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                // Apply globally so all endpoints are secured in Swagger unless they’re permitAll
                .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}

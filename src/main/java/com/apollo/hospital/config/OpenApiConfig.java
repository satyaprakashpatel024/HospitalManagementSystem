package com.apollo.hospital.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hospital Management System API",
                version = "1.0",
                description = "API documentation for My Hospital Management System.",
                contact = @Contact(
                        name = "Satya Prakash Patel",
                        email = "1008alexrider@gmail.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local Server")
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Hospital Management System API")
                        .version("1.0")
                        .description("This is the API documentation for My Hospital Management System.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}

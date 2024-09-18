package com.sadatscode.securityservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info=@Info(
               contact = @Contact(
                        name = "Sedat",
                        email = "memmedlisedat033@gmail.com",
                        url = "https://github.com/SedatMammadly"
                ),
                description = "Security service for ecommerce-backend project",
                title = "Open api for Sedat",
                license = @License(
                        url = "https://github.com/SedatMammadly"
                )
        ),
        servers = {
        @Server(
                description = "security-service",
                url = "http://localhost:8081"
        )
     }
 )
@SecurityScheme(
        name = "bearerAuthForPassword",
        description = "JWT auth",
        bearerFormat = "JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityScheme(
        name = "bearerForAuthenticationController",
        description = "JWT auth",
        bearerFormat = "JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

package me.paulojr.cooperfilme.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan("me.paulojr.cooperfilme.infra")
@OpenAPIDefinition(
        info = @Info(title = "CooperFilme API", version = "1.0", description = "Backend CooperFilme.",
                contact = @Contact(name = "Paulo Ricardo", email = "pauloricardo.jr1@gmail.com")
        ), security = @SecurityRequirement(name = "bearerAuth")
)
@SecuritySchemes(
        value = @SecurityScheme(
                name = "bearerAuth",
                in = SecuritySchemeIn.HEADER,
                description = "Bearer Token",
                paramName = "Authorization",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        )
)
public class WebServerConfig {
}

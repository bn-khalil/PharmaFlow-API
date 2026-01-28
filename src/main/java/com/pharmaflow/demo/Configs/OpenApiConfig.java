package com.pharmaflow.demo.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI createOAuthFlows() {
        OAuthFlow oauthFlow = new OAuthFlow()
                .authorizationUrl("http://localhost:9090/api/v1/login/oauth2/code/google")
                .tokenUrl("https://oauth2.googleapis.com/token")
                .scopes(new Scopes()
                        .addString("email", "Access to email")
                        .addString("profile", "Access to profile")
                );

        OAuthFlows oAuthFlows = new OAuthFlows().authorizationCode(oauthFlow);

        SecurityScheme oauth2Scheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("oauth2 flow")
                .name("GeneralOAuthFlow")
                .flows(oAuthFlows);

        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .description("jwt flow")
                .name("bearerAuth")
                .bearerFormat("JWT")
                .scheme("bearer");

        return new OpenAPI()
                .info(new Info().title("PharmaFlow API").version("1.0"))
                .components(new Components()
                        .addSecuritySchemes("OAuth2_Google", oauth2Scheme)
                        .addSecuritySchemes("Bearer_Auth", jwtScheme))
                .addSecurityItem(new SecurityRequirement()
                        .addList("OAuth2_Google")
                        .addList("Bearer_Auth"));
    }
}
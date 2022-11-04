package com.test.kopnus.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDoc()
    {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Test API").version("1.0.0").license(new License().name("Navirius").url("https://gitea.ranonline.co.id")));
    }


}

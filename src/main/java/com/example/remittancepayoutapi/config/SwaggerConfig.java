package com.example.remittancepayoutapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SeerBit Remittance Payout API")
                        .description("API for Remittance Payout Application")
                        .version("1.0-SNAPSHOT"))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub repository")
                        .url("https://github.com/AyeniAmanda/Remittance-Payout-API.git"));
    }
}

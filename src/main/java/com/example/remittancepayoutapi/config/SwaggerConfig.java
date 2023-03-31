package com.example.remittancepayoutapi.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /*
  Link to swagger api: http://localhost:8080/seerbit/api/v1/swagger-ui/index.html
  * */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("SeerbBit Remittance Payout Api")
                                .description("API for Remittance Payout Application")
                                .version("1.0-SNAPSHOT"))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Github link")
                                .url("https://github.com/AyeniAmanda/Remittance-Payout-APi.git"));
    }

}

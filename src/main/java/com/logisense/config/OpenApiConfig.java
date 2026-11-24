package com.logisense.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger UI configuration for interactive API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI logisenseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LogiSense API")
                        .description("Shipment management and tracking platform API")
                        .version("0.1.0"));
    }
}

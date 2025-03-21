package co.za.bsg.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.application.version}")
    private String applicationVersion;

    @Value("${swagger.application.name}")
    private String applicationName;

    @Value("${swagger.application.description}")
    private String applicationDescription;

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group(applicationName)
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI houseHoldAPI() {
        return new OpenAPI().info(new Info()
                .title("Anagram Service API")
                .description(applicationDescription)
                .version(applicationVersion));
    }
}

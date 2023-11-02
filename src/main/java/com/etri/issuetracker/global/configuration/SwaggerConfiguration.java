package com.etri.issuetracker.global.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "IssueTracker"
                , description = "API Statement for IssueTracker"
                , version = "v1")
)
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi post(){
        String[] paths = {"/post/**"};

        return GroupedOpenApi
                .builder()
                .group("IssueTracker Swagger v1-post")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi user(){
        String[] paths = {"/user/**"};

        return GroupedOpenApi
                .builder()
                .group("IssueTracker Swagger v1-user")
                .pathsToMatch(paths)
                .build();
    }
}

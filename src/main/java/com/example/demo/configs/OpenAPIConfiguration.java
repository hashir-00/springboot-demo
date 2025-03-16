package com.example.demo.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Value("${MY_SERVER_PORT}")
    private String myServerPort;

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:"+myServerPort);
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Hashir Halaldeen");
        myContact.setEmail("hashirHalaldeen00@gmail.com");

        Info information = new Info()
                .title("PET system API DOCs")
                .version("1.0")
                .description("This API exposes endpoints to this pet project.")
                .contact(myContact);
        Components component = new Components()
                .addSecuritySchemes("securityHeader",new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("missingParam"));
        return new OpenAPI().info(information).
                servers(List.of(server)).
                components(component).
                addSecurityItem(new SecurityRequirement().addList("securityHeader"));


    }




}
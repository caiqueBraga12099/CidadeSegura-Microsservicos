package com.github.caiqueBraga12099.cidadesegura.atendimentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("CidadeSegura - atendimentos").version("v1")
                        .description("Caique RM555532 e Vitor RM558516"))
                .addServersItem(new Server().url("/"));
    }
}

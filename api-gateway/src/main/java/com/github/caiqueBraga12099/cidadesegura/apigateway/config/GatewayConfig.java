package com.github.caiqueBraga12099.cidadesegura.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ocorrencias", route -> route.path("/api/v1/ocorrencias", "/api/v1/ocorrencias/**")
                        .uri("lb://ms-ocorrencias"))
                .route("atendimentos", route -> route.path("/api/v1/atendimentos", "/api/v1/atendimentos/**")
                        .uri("lb://ms-atendimentos"))
                .route("docs-ocorrencias", route -> route.path("/docs/ocorrencias")
                        .filters(filter -> filter.setPath("/v3/api-docs"))
                        .uri("lb://ms-ocorrencias"))
                .route("docs-atendimentos", route -> route.path("/docs/atendimentos")
                        .filters(filter -> filter.setPath("/v3/api-docs"))
                        .uri("lb://ms-atendimentos"))
                .build();
    }
}

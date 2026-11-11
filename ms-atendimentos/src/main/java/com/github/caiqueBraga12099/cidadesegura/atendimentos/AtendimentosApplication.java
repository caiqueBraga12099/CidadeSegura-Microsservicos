package com.github.caiqueBraga12099.cidadesegura.atendimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtendimentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtendimentosApplication.class, args);
    }
}

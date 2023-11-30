package com.example.gameservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    protected SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .cors().and().csrf().disable()
                .authorizeExchange(authorize -> authorize.anyExchange().authenticated())

                .oauth2Login(Customizer.withDefaults())

                .build();
    }

}

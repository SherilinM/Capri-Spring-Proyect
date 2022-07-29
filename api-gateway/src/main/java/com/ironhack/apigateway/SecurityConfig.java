package com.ironhack.apigateway;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/v1/places/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/favourites/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/users/{id}").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/ratings/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer()
                .jwt();

//        Okta.configureResourceServer401ResponseBody(http);

        return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}

package com.siget.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // libera login
                        .anyRequest().authenticated()            // exige token nas demais
                )
                .httpBasic(AbstractHttpConfigurer::disable) // desativa formulário e usa basic temporário
                .formLogin(AbstractHttpConfigurer::disable);   // desativa a tela de login

        return http.build();
    }
}

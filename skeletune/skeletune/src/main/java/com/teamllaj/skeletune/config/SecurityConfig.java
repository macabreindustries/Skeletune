package com.teamllaj.skeletune.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (común para APIs REST)
                .csrf(csrf -> csrf.disable())


                // 2. Configurar las reglas de autorización

                .authorizeHttpRequests(auth -> auth
                        // **CLAVE:** Permite todas las solicitudes a /api/v1/notifications
                        .requestMatchers("/api/v1/notifications").permitAll()

                        // (Opcional) Asegura cualquier otra ruta que no sea /api/
                        .anyRequest().authenticated()
                )

                // 3. Habilita la autenticación básica (si la necesitas para otras rutas)
                .httpBasic(withDefaults());

        return http.build();
    }
}
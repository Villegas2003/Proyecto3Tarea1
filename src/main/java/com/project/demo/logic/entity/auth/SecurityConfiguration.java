package com.project.demo.logic.entity.auth;

import com.project.demo.logic.entity.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        // Nuevas líneas que especifican permisos según los roles
                        .requestMatchers(HttpMethod.GET, "/productos/**").hasAnyRole("SUPER_ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categorias/**").hasAnyRole("SUPER_ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/categorias/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

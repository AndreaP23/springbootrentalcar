package com.si2001.webapp.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtWebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(JwtWebSecurityConfig.class);

    private final JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    // Definiamo SHARED_MATCHER per gli endpoint comuni
    private static final String[] SHARED_MATCHER = { "/listveicoli/**", "/listprenotazioni/**" };

    // Definiamo gli endpoint specifici per SuperUser
    private static final String[] SUPERUSER_MATCHER = { "/superuser/deletePrenotazione/**", "/modifica/**", "/auth/superuser/listuser", "/auth/superuser/delete/**" };

    // Definiamo gli endpoint specifici per Customer
    private static final String[] CUSTOMER_MATCHER = { "/salva/**" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(SHARED_MATCHER).hasAnyAuthority("ROLE_1", "ROLE_2")
                        .requestMatchers(SUPERUSER_MATCHER).hasAuthority("ROLE_1")
                        .requestMatchers(CUSTOMER_MATCHER).hasAuthority("ROLE_2")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://editor.swagger.io", "http://localhost:4200"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

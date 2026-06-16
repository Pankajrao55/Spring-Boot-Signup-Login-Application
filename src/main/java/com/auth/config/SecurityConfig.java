package com.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * BCryptPasswordEncoder bean — used across the app for hashing passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security filter chain — defines which URLs are public vs protected
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity (enable in production!)
            .csrf(csrf -> csrf.disable())

            // URL access rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().permitAll()  // allow all for now (can lock down later)
            )

            // Disable Spring Security's default login form (we use our own)
            .formLogin(form -> form.disable())

            // Disable default logout
            .logout(logout -> logout.disable());

        return http.build();
    }
}

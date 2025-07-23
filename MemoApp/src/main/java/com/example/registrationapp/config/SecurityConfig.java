package com.example.registrationapp.config;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login").permitAll() 
                .requestMatchers("/create", "/edit/**").authenticated() 
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable()) 
            .sessionManagement(session -> session.sessionFixation().newSession()) 
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .formLogin(form -> form
                .loginPage("/login").permitAll()
                .usernameParameter("email") 
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true) 
                .failureUrl("/login?error=true") 
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login") 
                .invalidateHttpSession(true) 
                .deleteCookies("JSESSIONID") 
                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

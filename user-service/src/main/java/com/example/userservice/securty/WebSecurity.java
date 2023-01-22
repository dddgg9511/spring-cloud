package com.example.userservice.securty;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class WebSecurity{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // https://github.com/spring-projects/spring-security/issues/11337
        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#page-title
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/**").permitAll();
        });

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() {
        return new AuthenticationFilter();
    }
}

package com.example.userservice.securty;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity{
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // https://github.com/spring-projects/spring-security/issues/11337
        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#page-title
        http.authorizeHttpRequests(auth -> {
            try {
                auth.requestMatchers("/**").permitAll()
                        .and()
                        .addFilter(getAuthenticationFilter(authenticationManager(http)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return http.build();
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .build();
    }
}

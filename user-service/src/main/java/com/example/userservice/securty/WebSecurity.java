package com.example.userservice.securty;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ObservationAuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
//    private final AuthenticationManagerBuilder builder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // https://github.com/spring-projects/spring-security/issues/11337
        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#page-title
        http.authorizeHttpRequests(auth -> {
            try {
                auth.requestMatchers("/login").permitAll()
                        .and()
                        .addFilter(getAuthenticationFilter());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return http.build();
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    private AuthenticationManager authenticationManager() throws Exception {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.afterPropertiesSet();
        ProviderManager manager = new ProviderManager(Arrays.<AuthenticationProvider>asList(provider));

        return manager;
    }
}

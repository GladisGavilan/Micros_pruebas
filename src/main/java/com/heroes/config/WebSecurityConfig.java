package com.heroes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.heroes.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig{

    @Autowired
    JwtAuthenticationFilter filter;
		
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
            .csrf(csrf -> 
                csrf
                .disable())
            .authorizeHttpRequests(authRequest ->
              authRequest
              	.requestMatchers(HttpMethod.GET, "/heroesOperations/**").authenticated()
              	.requestMatchers(HttpMethod.POST, "/heroesOperations/**").hasAuthority("ADMIN")
              	.requestMatchers(HttpMethod.PUT, "/heroesOperations/**").hasAuthority("ADMIN")
              	.requestMatchers(HttpMethod.DELETE, "/heroesOperations/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll())
                .httpBasic(Customizer.withDefaults())
            .addFilter(filter);
           return http.build();
    }
	
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
  }



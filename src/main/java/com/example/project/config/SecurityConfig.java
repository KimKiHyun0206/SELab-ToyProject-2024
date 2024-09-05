package com.example.project.config;

import com.example.project.config.JwtSecurityConfig;
import com.example.project.jwt.component.JwtAccessDeniedHandler;
import com.example.project.jwt.component.JwtAuthenticationEntryPoint;
import com.example.project.jwt.component.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf(AbstractHttpConfigurer::disable)

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )

            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                        "/api/hello",
                        "/api/authenticate",
                        "/api/signup",
                        "/",
                        "/solutions",
                        "/solutions/list",
                        "/users/login",
                        "/users/register"
                ).permitAll()
                //.requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
            )

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // enable h2-console
            .headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )

            .with(jwtSecurityConfig, customizer -> {});
        return http.build();
    }
}
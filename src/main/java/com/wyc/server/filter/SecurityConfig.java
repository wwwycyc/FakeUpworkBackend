package com.wyc.server.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 禁用 CSRF 防护（对于 REST API 应用，通常是禁用的）
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/uploads/**").authenticated() // 需要认证的路径
                                .anyRequest().permitAll() // 其他请求可以自由访问
                )
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();  // 构建并返回 HttpSecurity 配置
    }
}

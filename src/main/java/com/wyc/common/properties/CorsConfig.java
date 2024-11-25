package com.wyc.common.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置跨域规则
        registry.addMapping("/**")           // 允许跨域的路径
                .allowedOrigins("http://8.218.109.21")  // 允许的域名
                .allowedMethods("GET", "POST", "PUT", "DELETE")   // 允许的请求方式
                .allowedHeaders("*")         // 允许的请求头
                .allowCredentials(true)      // 是否允许带有凭证
                .maxAge(3600);               // 预检请求的最大有效期（单位：秒）
    }
}

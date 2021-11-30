package com.example.demo.config;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.yaml.snakeyaml.events.ImplicitTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven
 */
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        List<String> list=new ArrayList<>();
        CollectionUtils.addAll(list,"http://localhost:8080","http://localhost:8081","http://127.0.0.1:8081","http://steven.study.com","http://localhost:3000");
        config.setAllowedOrigins(list);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}

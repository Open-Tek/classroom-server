package com.knowwhere.classroom.utils.tokens.config;

import com.knowwhere.classroom.utils.tokens.interceptor.TokenInterceptorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {


    @Bean
    public TokenInterceptorHandler createTokenInterceptorHandler(){
        return new TokenInterceptorHandler();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println("REGI");
        registry.addInterceptor(this.createTokenInterceptorHandler()).excludePathPatterns("/api/v1/token/**");
    }
}

package com.knowwhere.classroom;

import com.knowwhere.classroom.utils.tokens.interceptor.TokenInterceptorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.MappedInterceptor;

@SpringBootApplication
public class ClassroomApplication {

    /*@Bean
    public MappedInterceptor myInterceptor(){
        return new MappedInterceptor(null, new String[]{"api/v1/token/**" }, new TokenInterceptorHandler());
    }*/



    public static void main(String[] args) {
        SpringApplication.run(ClassroomApplication.class, args);
    }

}


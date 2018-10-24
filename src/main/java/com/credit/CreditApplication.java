package com.credit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 *
 *
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.credit.dao"})
public class CreditApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CreditApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(CreditApplication.class, args);
    }
}

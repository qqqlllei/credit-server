package com.credit.configurer;

import com.credit.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lilei on 2017/5/3.
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {


    @Bean
    AuthInterceptor authInterceptor(){
        return  new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

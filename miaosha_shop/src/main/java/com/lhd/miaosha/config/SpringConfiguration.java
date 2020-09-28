package com.lhd.miaosha.config;

import com.lhd.miaosha.interceptor.AccessLimitInterceptor;
import com.lhd.util.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SpringConfiguration implements WebMvcConfigurer {
   @Autowired
    AccessLimitInterceptor accessLimitInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
    }
}

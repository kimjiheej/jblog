package com.poscodx.jblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.config.web.FileUploadConfig;
import com.poscodx.jblog.config.web.LocaleConfig;
import com.poscodx.jblog.config.web.MvcConfig;
import com.poscodx.jblog.config.web.SecurityConfig;
import com.poscodx.jblog.security.AuthInterceptor;
import com.poscodx.jblog.security.LoginInterceptor;
import com.poscodx.jblog.security.LogoutInterceptor;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class, FileUploadConfig.class, LocaleConfig.class, SecurityConfig.class})
@ComponentScan({"com.poscodx.jblog.controller", "com.poscodx.jblog.service", "com.poscodx.jblog.security", "com.poscodx.jblog.repository"})
public class WebConfig {
}


